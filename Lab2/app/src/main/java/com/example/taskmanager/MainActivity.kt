package com.example.taskmanager

import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.PhotosTab.value
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.Datasource
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.taskIdCounter

import com.example.taskmanager.ui.theme.TaskManagerTheme
import javax.sql.DataSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                    )
                }
        }
    }
}

//Entry point composable.
@Composable
fun MainScreen() {
    var taskInput by remember { mutableStateOf("") }
    var taskList = remember { mutableStateListOf<Task>() }

    if (taskList.isEmpty())
    {
        taskList.addAll(Datasource().loadTasks()) // Initialize the task list using the Datasource
    }

    Column() {
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
                taskList = taskList //Datasource().loadTasks() //display task list
            )
        }
    }
}



//Composable for the TextField and "Add Task" button.
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
        modifier = modifier
    )

    Button(
        onClick = onClick
    ) {
        Text(
            stringResource(R.string.button_add_task_label) //button label
        )
    }
}

//Composable for an individual task item (Checkbox, Text, Delete button).
@Composable
fun TaskItem(
        task: Task
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Text(
            text = task.taskDescription, //displays taskDescription text.
            modifier = Modifier
                .padding(8.dp)
            )

//        //delete icon button
//        IconButton(
//            onClick = onDelete
//        ) {
//            Icon(
//                imageVector = Icons.Filled.Delete,
//                contentDescription = "Remove Task"
//            )
//        }
    }
}

//Composable for displaying the list of tasks.
@Composable
fun TaskList(
        taskList: List<Task>,
        modifier: Modifier = Modifier
) {
    LazyColumn(modifier = Modifier) {
        items(taskList) { task ->
            TaskItem(
                task = task
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