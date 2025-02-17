package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.ui.theme.TaskManagerTheme

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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun TaskManagerLayout() {
    var taskInput by remember { mutableStateOf("") }
    Row() {
        AddTaskField(
            label = R.string.text_field_label,  // Pass the string resource ID
            keyboardOptions = KeyboardOptions.Default,
            value = taskInput,
            onValueChange = { taskInput = it }, // Update taskInput state when text changes
            modifier = Modifier.padding(16.dp)
        )
            TaskButton()
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
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun TaskButton() {
    Button(onClick = { /*TODO*/ }) {
        Text(stringResource(R.string.button_add_task))
    }
}

@Composable
fun TaskList() {

}







@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskManagerTheme {
        TaskManagerLayout()
    }
}