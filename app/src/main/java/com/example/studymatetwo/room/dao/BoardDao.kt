package com.example.studymatetwo.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studymatetwo.room.entity.BoardEntity

@Dao
interface BoardDao {
    @Query("SELECT * FROM board_table")
    fun getAllData() : LiveData<List<BoardEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dataList : List<BoardEntity>)
}