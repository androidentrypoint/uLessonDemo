package com.example.ulessondemo.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = ChapterEntity::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LessonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val iconUrl: String,
    val mediaUrl: String,
    val chapterId: Int,
    val lastWatched: String?
)