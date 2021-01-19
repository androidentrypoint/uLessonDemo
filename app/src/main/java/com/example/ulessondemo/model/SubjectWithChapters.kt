package com.example.ulessondemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubjectWithChapters(
    val subject: Subject,
    val chapters: List<ChapterWithLessons>
) : Parcelable
