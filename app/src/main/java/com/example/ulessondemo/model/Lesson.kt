package com.example.ulessondemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lesson(
    val id: Int,
    val name: String,
    val iconUrl: String,
    val mediaUrl: String,
    val chapterId: Int,
    val lastWatched: String?
) : Parcelable