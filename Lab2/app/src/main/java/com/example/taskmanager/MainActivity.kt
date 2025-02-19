package com.example.taskmanager

import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.PhotosTab.value
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.Datasource
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.taskIdCounter
import com.example.taskmanager.ui.theme.Pink80

import com.example.taskmanager.ui.theme.TaskManagerTheme
import javax.sql.DataSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
            }
        }
    }
}

//Entry point composable.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var taskInput by remember { mutableStateOf("") }
    var taskList = remember { mutableStateListOf<Task>() }


    if (taskList.isEmpty()) //initialize list if empty
    {
        taskList.addAll(Datasource().loadTasks()) // Initialize the task list using the Datasource
    }



    Column(
        modifier = Modifier
            .padding(16.dp)     //applies padding around all components.
    ) {

        Spacer(modifier = Modifier.height(16.dp))   //adds space between title and textbox/button

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Task Manager"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))   //adds space between title and textbox/button

        Row() {
            TaskInputField(
                label = R.string.text_field_enter_task_label,  // Pass the string resource ID
                keyboardOptions = KeyboardOptions.Default,
                value = taskInput,
                onValueChange = { taskInput = it }, // Update taskInput state when text changes
                onClick = {
                    if (
                        taskInput.isNotBlank()
                        ) {
                            // Add new task to the list
                            val newTask =
                                Task(
                                    id = taskList.size,
                                    taskDescription = taskInput,
                                    false
                                )
                            taskList.add(newTask)
                            taskInput = "" //resets input field
                        }
                },
                modifier = Modifier.padding(16.dp)
            )
        }

        Row() {
            TaskList(
                taskList = taskList, //Datasource().loadTasks() //display task list
                onDelete = { task -> taskList.remove(task) }, //removes task from taskList
                onCheckboxChange = { task, isChecked ->
                    // Update the task's checkbox state and trigger recomposition
                    val index = taskList.indexOf(task)
                    if (index != -1) {
                        taskList[index] =
                            task.copy(checkboxIsCompleted = isChecked) // Update task state
                    }
                }
            )
        }
    }
}

//Composable for the TextField and "Add Task" button.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputField(
    //stateless composable function
    @StringRes label: Int, //To denote that the label parameter is expected to be a string resource reference, annotate the function parameter with the @StringRes annotation
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) }, //text field: enter task label
        keyboardOptions = keyboardOptions,
        modifier = Modifier

    )

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Magenta) //sets "Add Task" button background color.
    ) {
        Text(
            stringResource(R.string.button_add_task_label) //button label
        )
    }
}

//Composable for an individual task item (Checkbox, Text, Delete button).
@Composable
fun TaskItem(
        task: Task,
        onDelete: () -> Unit,
        onCheckboxChange: (Boolean) -> Unit,
        modifier: Modifier = Modifier
) {
    val backgroundCompletedTask =
        if (task.checkboxIsCompleted) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onPrimary
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(backgroundCompletedTask)
    ) {

        //a Checkbox to mark the task as completed.
        Checkbox(
            checked = task.checkboxIsCompleted,
            onCheckedChange = { isChecked -> onCheckboxChange(isChecked) }
        )


        //Text element displaying the task description.
        Text(
            text = task.taskDescription, //displays taskDescription text.
            modifier = Modifier
                .padding(8.dp)
            )

        //horizontal spacer, pushes delete button to far right.
        Spacer(modifier = Modifier.weight(1f))

        //delete icon (IconButton) to remove a task.
        IconButton(
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove Task"
            )
        }
    }
}

//Composable for displaying the list of tasks.
@Composable
fun TaskList(
        taskList: List<Task>,
        onDelete: (Task) -> Unit,
        onCheckboxChange: (Task, Boolean) -> Unit,
        modifier: Modifier = Modifier
) {
    //A vertically scrolling list (LazyColumn) of tasks where each task has: Checkbox, Text, IconButton.
    LazyColumn(modifier = Modifier) {
        items(taskList) { task ->
            TaskItem(
                task = task,
                onDelete = { onDelete(task) },
                onCheckboxChange = { isChecked -> onCheckboxChange(task, isChecked) },
                modifier = modifier
                    .padding(8.dp)  //adds spacing (8dp) between list items.
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskManagerTheme {
        MainScreen()
    }
}