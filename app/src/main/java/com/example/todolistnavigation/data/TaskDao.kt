package com.example.todolistnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Обьекты доступа к данным
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT is_checked FROM tasks")
    fun getCheckedTasks(): LiveData<List<Boolean>>

    @Insert
    fun addTask(user: Task)

    @Query("DELETE FROM tasks WHERE itemId = :id")
    fun deleteTask(id: Int)

    @Query("UPDATE tasks SET is_checked = NOT is_checked WHERE itemId = :taskId ")
    fun toggleChecked(taskId: Int)
}