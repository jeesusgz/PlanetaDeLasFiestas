package com.jesus.planetadelasfiestas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.jvm.java

@Database(entities = [AlbumEntity::class, CommentEntity::class], version = 8)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "albums_db"
            )
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}

