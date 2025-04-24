package com.example.todolistnavigation.features


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistnavigation.TodoViewModel
import com.example.todolistnavigation.ui.theme.mainColor


@Composable
fun TaskSheet ( vm: TodoViewModel = viewModel()){

    val tasks by vm.taskList.observeAsState(initial = emptyList())

    Text(
        text = "Текущие задачи:",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(tasks) { index, task ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = mainColor
                )
            ) {
                Text(
                    text = getFirstTwoWords(task.item),
                    modifier = Modifier.padding(16.dp),
                    textDecoration = if ( task.chekItem ) TextDecoration.LineThrough else TextDecoration.None
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddItem(
    vm: TodoViewModel = viewModel()
) {
//    var newTaskText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = vm.taskItem,
                onValueChange = { vm.setTask(it) },
                label = { Text("Новая задача") },
                placeholder = { Text("Введите текст") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE3F2FD),
                    focusedContainerColor = Color(0xFFBBDEFB)
                ),
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    vm.addTask()
                },
//                enabled = newTaskText.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить",
                    tint = if (vm.checkedState) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        TaskSheet (vm)
    }
}