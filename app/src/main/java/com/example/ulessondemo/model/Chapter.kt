package com.example.ulessondemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chapter(
    val id: Int,
    val name: String,
    val subjectId: Int
) : Parcelable