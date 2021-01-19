package com.example.ulessondemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Subject(
    val id: Int,
    val name: String,
    val iconUrl: String,
    val drawableAndColorIndex: Int
) : Parcelable