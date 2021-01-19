package com.example.ulessondemo.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ulessondemo.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _homeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeStateLiveData = _homeStateFlow.flatMapLatest { repository.getHomeItems() }.asLiveData()

    fun refresh() {
        _homeStateFlow.value = _homeStateFlow.value.not()
    }
}