package com.example.todoapplication.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToDo(todo: ToDo)

    @Query("SELECT * FROM todo ORDER BY id ASC")
    fun readAllData(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE id =:id")
    fun fetchToDoById(id: String): Flow<ToDo>

    @Query("DELETE FROM todo")
    suspend fun deleteAll()
}