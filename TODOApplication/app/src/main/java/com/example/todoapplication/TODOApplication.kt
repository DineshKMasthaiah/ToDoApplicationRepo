package com.example.todoapplication

import android.app.Application
import com.example.todoapplication.model.ToDo
import com.example.todoapplication.model.ToDoDatabase
import com.example.todoapplication.model.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TODOApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ToDoDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ToDoRepository(database.toDoDao()) }
}