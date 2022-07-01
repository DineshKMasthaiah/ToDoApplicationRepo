package com.example.todoapplication.viewmodel.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.model.ToDo
import com.example.todoapplication.model.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ToDoViewModel(private val repository: ToDoRepository): ViewModel() {

      val readAllData:LiveData<List<ToDo>> =  repository.readAllData.asLiveData()
    fun fetchToDoById(id: String): Flow<ToDo> = repository.fetchToDoById(id)

    fun addToDo(user: ToDo){
        viewModelScope.launch{
            repository.addToDo(user)
        }
    }

}