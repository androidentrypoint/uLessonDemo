package com.example.ulessondemo.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _homeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val homeStateLiveData = _homeStateFlow.flatMapLatest { repository.getHomeItems() }.asLiveData()

    fun refresh() {
        _homeStateFlow.value = _homeStateFlow.value.not()
    }

    fun updateLastWatched(lesson: Lesson) {
        viewModelScope.launch {
            repository.updateLastWatched(lesson)
        }
    }
}