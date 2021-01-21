package com.example.ulessondemo.repository

import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.state.HomeState
import com.example.ulessondemo.util.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getHomeItems(): Flow<NetworkStatus<HomeState>>

    suspend fun updateLastWatched(lesson: Lesson)
}