package com.example.ulessondemo.network.model

import com.example.ulessondemo.network.NetworkResponseDTO
import com.example.ulessondemo.network.NetworkResponseStatus
import com.google.gson.annotations.SerializedName

data class SubjectResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("subjects")
    val subjects: List<SubjectDTO>
) : NetworkResponseStatus, NetworkResponseDTO<List<SubjectDTO>> {
    override val isSuccessful: Boolean
        get() = status == "success"
    override val dto: List<SubjectDTO>
        get() = subjects
}
