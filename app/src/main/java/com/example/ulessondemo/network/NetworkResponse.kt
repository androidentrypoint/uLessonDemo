package com.example.ulessondemo.network

import com.google.gson.annotations.SerializedName

data class NetworkResponse<T, D>(
    @SerializedName("data")
    val data: T?
) where T : NetworkResponseStatus, T : NetworkResponseDTO<D> {
    val dto: D?
        get() = data?.dto
}

interface NetworkResponseStatus {
    val isSuccessful: Boolean
}

interface NetworkResponseDTO<T> {
    val dto: T?
}


