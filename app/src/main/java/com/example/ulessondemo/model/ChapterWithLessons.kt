package com.example.ulessondemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChapterWithLessons(
    val chapter: Chapter,
    val lessons: List<Lesson>
) : Parcelable
