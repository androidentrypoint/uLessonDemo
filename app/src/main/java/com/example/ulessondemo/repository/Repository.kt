package com.example.ulessondemo.repository

import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.state.HomeState
import kotlinx.coroutines.flow.Flow
import org.akefestival.core.util.NetworkStatus

interface Repository {

    fun getHomeItems(): Flow<NetworkStatus<HomeState>>

    suspend fun updateLastWatched(lesson: Lesson)
}