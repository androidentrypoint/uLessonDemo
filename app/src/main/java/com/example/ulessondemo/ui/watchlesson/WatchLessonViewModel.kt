package com.example.ulessondemo.ui.watchlesson

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.repository.Repository
import kotlinx.coroutines.launch

class WatchLessonViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    fun updateLastWatched(lesson: Lesson) {
        viewModelScope.launch {
            repository.updateLastWatched(lesson)
        }
    }
}