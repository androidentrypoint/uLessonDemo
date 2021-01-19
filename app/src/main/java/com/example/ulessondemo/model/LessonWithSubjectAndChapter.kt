package com.example.ulessondemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LessonWithSubjectAndChapter(
    val lesson: Lesson,
    val chapter: Chapter,
    val subject: Subject
) : Parcelable
