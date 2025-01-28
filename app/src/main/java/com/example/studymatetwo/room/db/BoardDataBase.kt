package com.example.studymatetwo.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studymatetwo.room.dao.BoardDao
import com.example.studymatetwo.room.entity.BoardEntity

@Database(entities = [BoardEntity::class], version = 1)
abstract class BoardDataBase : RoomDatabase() {

    abstract fun boardDao() :BoardDao

    companion object {
        @Volatile
        private var INSTANCE : BoardDataBase? = null

        fun getDatabase(
            context : Context
        ) : BoardDataBase {
            return INSTANCE ?:synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BoardDataBase::class.java,
                    "board_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}