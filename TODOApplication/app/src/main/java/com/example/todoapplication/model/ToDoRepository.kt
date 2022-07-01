package com.example.todoapplication.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val todoDao: ToDoDao) {

    val readAllData: Flow<List<ToDo>> = todoDao.readAllData()

    @WorkerThread
    suspend fun addToDo(toDo: ToDo){
        todoDao.addToDo(toDo)
    }

    @WorkerThread
    fun fetchToDoById(id: String): Flow<ToDo> = todoDao.fetchToDoById(id)



}