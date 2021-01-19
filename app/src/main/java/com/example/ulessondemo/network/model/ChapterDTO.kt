package com.example.ulessondemo.network.model

import com.google.gson.annotations.SerializedName

data class ChapterDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("lessons")
    val lessons: List<LessonDTO>
)