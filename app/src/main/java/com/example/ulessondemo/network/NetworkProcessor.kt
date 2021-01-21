package com.example.ulessondemo.network

import com.example.ulessondemo.util.NetworkStatus
import retrofit2.Response

interface NetworkProcessor {

    suspend fun <R, D> processNetworkResponse(block: suspend () -> Response<NetworkResponse<R, D>>): NetworkStatus<D> where R : NetworkResponseStatus, R : NetworkResponseDTO<D>
}