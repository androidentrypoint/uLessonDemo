package com.example.ulessondemo.state

import com.example.ulessondemo.model.LessonWithSubjectAndChapter
import com.example.ulessondemo.model.SubjectWithChapters

data class HomeState(
    val subjects: List<SubjectWithChapters>,
    val recentlyWatchedLessons: List<LessonWithSubjectAndChapter>
)
