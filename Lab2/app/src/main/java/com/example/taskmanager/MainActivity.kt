package com.example.taskmanager

import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
                    TaskManagerLayout(

                    )
                }
        }
    }
}

@Composable
fun TaskManagerLayout() {
    var taskInput by remember { mutableStateOf("") }
    val taskList = remember { mutableStateListOf<Task>() }

    Column() {
        Row() {
            AddTaskField(
                label = R.string.text_field_label,  // Pass the string resource ID
                keyboardOptions = KeyboardOptions.Default,
                value = taskInput,
                onValueChange = { taskInput = it }, // Update taskInput state when text changes
                modifier = Modifier.padding(16.dp)
            )

            TaskButton(
                onClick = {
                    if (taskInput.isNotBlank()) {
                        // Add new task to the list
                        val newTask = Task(id = taskList.size, taskDescription = taskIdCounter)
                        taskList.add(newTask)
                        taskInput = "" // Clear the input field
                    }
                }
            )
        }

        Row() {
        TaskList(taskList = Datasource().loadTasks())
        }
    }
}

@Composable
fun AddTaskField(
    //stateless composable function
    @StringRes label: Int, //To denote that the label parameter is expected to be a string resource reference, annotate the function parameter with the @StringRes annotation
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = { /*TODO */ },//onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Composable
fun TaskButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(stringResource(R.string.button_add_task))
    }
}

@Composable
fun TaskInformation(task: Task) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Text(
            text = stringResource(id = task.taskDescription), //displays taskDescription text.
            modifier = Modifier
                .padding(8.dp)
            )
    }
}

@Composable
fun TaskList(
        taskList: List<Task>,
        modifier: Modifier = Modifier
) {
    LazyColumn(modifier = Modifier) {
        items(taskList) { task ->
            TaskInformation(
                task = task
            )
        }
    }
}







@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskManagerTheme {
        TaskManagerLayout()
    }
}