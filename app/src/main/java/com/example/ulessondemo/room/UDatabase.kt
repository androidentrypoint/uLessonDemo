package org.akefestival.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ulessondemo.room.dao.ChapterDao
import com.example.ulessondemo.room.dao.LessonDao
import com.example.ulessondemo.room.dao.SubjectDao
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.entity.SubjectEntity

@Database(
    entities = [SubjectEntity::class, ChapterEntity::class, LessonEntity::class],
    version = 1
)
abstract class UDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun chapterDao(): ChapterDao
    abstract fun lessonDao(): LessonDao

    companion object {

        @Volatile
        private var INSTANCE: UDatabase? = null

        fun getInstance(context: Context): UDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        )
                            .also {
                                INSTANCE = it
                            }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UDatabase::class.java,
                "UDB"
            )
                .build()
    }


}