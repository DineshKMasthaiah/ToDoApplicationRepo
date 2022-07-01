package com.example.todoapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "todo")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val dateAndTime: String,
    val alarmType: Int
)