package com.example.ulessondemo.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubjectEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val iconUrl: String,
    val drawableAndColorIndex: Int
)