package com.example.todolistnavigation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolistnavigation.data.Task
import com.example.todolistnavigation.data.TaskDatabase
import com.example.todolistnavigation.data.TaskRepository

class TodoViewModel(application: Application) :ViewModel(){

    val taskList: LiveData<List<Task>>
    val checkedCount: LiveData<List<Boolean>>
    private val repository: TaskRepository
    var taskItem by mutableStateOf("")
    var checkedState by mutableStateOf(false)

    init {
        val taskDb = TaskDatabase.getInstance(application)
        val taskDao = taskDb.userDao()
        repository = TaskRepository(taskDao)
        taskList = repository.taskList
        checkedCount = repository.checkCount
    }

    fun setTask(value: String){
        taskItem =value
    }

    fun setCheckedStates(value: Boolean){
        checkedState = value
    }

    fun addTask(){
        repository.addTask(Task(taskItem, checkedState))
    }

    fun toggleTaskChecked(taskId: Int){
        repository.toggleTaskChecked(taskId)
    }

    fun deleteTask(id: Int){
        repository.deleteTask(id)
    }

}