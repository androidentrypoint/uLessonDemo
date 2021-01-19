package com.example.ulessondemo.network.model

import com.google.gson.annotations.SerializedName

data class SubjectDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("icon")
    val iconUrl: String,
    @SerializedName("chapters")
    val chapters: List<ChapterDTO>
)

