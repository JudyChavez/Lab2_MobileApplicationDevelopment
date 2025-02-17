package com.example.taskmanager.data

import android.app.ActivityManager.TaskDescription
import androidx.annotation.StringRes
import com.example.taskmanager.R


/**
 * A data class to represent the information presented in the task list
 */
data class Task(
    val id: Int,
    @StringRes val taskDescription: Int,
    var checkboxIsCompleted: Boolean = false //complete or not complete.
)

var taskIdCounter = 0 //initializes id counter


// List of tasks and the information that you will use as the data in your app.
class Datasource() {
    fun loadTasks(): List<Task> {
        return mutableListOf<Task>(
            Task(id = taskIdCounter++, R.string.task_1, true),
            Task(id = taskIdCounter++, R.string.task_2, true),
            Task(id = taskIdCounter++, R.string.task_3, true),
            Task(id = taskIdCounter++, R.string.task_4, true),
            Task(id = taskIdCounter++, R.string.task_5, true)

        )
    }
}



// List of tasks and the information that you will use as the data in your app.
//val tasks = mutableListOf(
//    Task(id = taskIdCounter++, R.string.task_1),
//    Task(id = taskIdCounter++, R.string.task_2),
//    Task(id = taskIdCounter++, R.string.task_3),
//    Task(id = taskIdCounter++, R.string.task_4),
//    Task(id = taskIdCounter++, R.string.task_5)
//
//)