package com.example.taskmanager.data

import android.app.ActivityManager.TaskDescription
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import com.example.taskmanager.R


/**
 * A data class to represent the information presented in the task list
 */
data class Task(
    val id: Int,
    val taskDescription: String,    //@StringRes val taskDescription: Int,
    var checkboxIsCompleted: Boolean //complete or not complete.
)

var taskIdCounter = 0 //initializes id counter



// Seeded data for testing, List of tasks and the information that you will use as the data in your app.
class Datasource() {
    fun loadTasks(): List<Task> {
        return mutableStateListOf<Task>(
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 1", false),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 2", false),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 3", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 4", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 5", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 6", false),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 7", false),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 8", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 9", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 10", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 11", false),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 12", false),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 13", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 14", true),
//            Task(id = taskIdCounter++, taskDescription = "Datasource - Test 15", true)

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