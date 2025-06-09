package com.jesus.planetadelasfiestas.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "albums_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideAlbumDao(database: AppDatabase): AlbumDao =
        database.albumDao()

    @Provides
    fun provideCommentDao(database: AppDatabase): CommentDao =
        database.commentDao()
}