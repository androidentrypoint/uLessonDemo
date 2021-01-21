package com.example.ulessondemo.network

import com.example.ulessondemo.util.DispatcherProvider
import com.example.ulessondemo.util.NetworkConstants
import com.example.ulessondemo.util.NetworkStatus
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NetworkProcessorImpl @Inject constructor(
    val dispatcherProvider: DispatcherProvider
) : NetworkProcessor {

    @Suppress("OVERRIDE_BY_INLINE")
    override suspend inline fun <R, D> processNetworkResponse(
        crossinline block: suspend () -> Response<NetworkResponse<R, D>>
    ): NetworkStatus<D> where R : NetworkResponseStatus, R : NetworkResponseDTO<D> {
        return withContext(dispatcherProvider.io()) {
            try {
                val response = block()
                when {
                    response.isSuccessful && response.body()?.data?.isSuccessful == true -> {
                        return@withContext NetworkStatus.Success(response.body()?.dto!!)
                    }
                    else -> {
                        return@withContext NetworkStatus.Error(
                            NetworkConstants.UNKNOWN_NETWORK_EXCEPTION
                        )
                    }
                }
            } catch (e: Exception) {
                return@withContext NetworkStatus.Error(
                    e.message ?: NetworkConstants.UNKNOWN_NETWORK_EXCEPTION
                )
            }
        }
    }
}