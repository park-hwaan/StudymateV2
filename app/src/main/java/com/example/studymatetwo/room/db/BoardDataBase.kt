package com.example.studymatetwo.room.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studymatetwo.room.dao.BoardDao
import com.example.studymatetwo.room.entity.BoardEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [BoardEntity::class], version = 2)
abstract class BoardDataBase : RoomDatabase() {
    abstract fun boardDao(): BoardDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BoardDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            BoardDataBase::class.java,
            "board_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBoardDao(database: BoardDataBase): BoardDao {
        return database.boardDao()
    }
}