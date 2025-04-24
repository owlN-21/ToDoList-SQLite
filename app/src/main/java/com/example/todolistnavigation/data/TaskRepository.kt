package com.example.todolistnavigation.data

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepository(private val itemDao: TaskDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main) // работа с UI

    val taskList: LiveData<List<Task>> = itemDao.getTasks() // наблюдаемое значение

    val checkCount: LiveData<List<Boolean>> = itemDao.getCheckedTasks()

    fun addTask(task: Task){
        // Dispatchers.IO - работа с сетью/диском
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.addTask(task)
        }
    }

    fun deleteTask(id: Int){
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.deleteTask(id)
        }
    }

    fun toggleTaskChecked(taskId: Int){
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.toggleChecked(taskId)
        }
    }

    companion object {
        private var instance: TaskRepository? = null

        fun init(context: Context) {
            if (instance == null) {
                val taskDb = TaskDatabase.getInstance(context)
                val taskDao = taskDb.userDao()
                instance = TaskRepository(taskDao)
            }
        }

        fun getInstance (): TaskRepository {
            return instance!!
        }
    }
}