package com.example.todolistnavigation.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistnavigation.TodoViewModel
import com.example.todolistnavigation.navigation.NavRoutes
import com.example.todolistnavigation.ui.theme.mainColor


@Composable
fun TaskItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onClick: ()  -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable { onClick() }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = mainColor
            )
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
        )

        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Удалить")
        }
    }
}


@Composable
fun AddTaskFooter(
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .width(200.dp)
                .height(48.dp),
            onClick = { navController.navigate(NavRoutes.AddItem.route) },
            colors = ButtonDefaults.buttonColors(mainColor)
        ) {
            Text(text = "Add Task")
        }
    }
}


@Composable
fun TodoUI(navController: NavController, vm: TodoViewModel) {

    //LiveData в State
    val tasks by vm.taskList.observeAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            AddTaskFooter(navController)
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 12.dp, start = 6.dp, end = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(tasks) { index, task ->
                TaskItem(
                    text = getFirstTwoWords(task.item),
                    isChecked = task.chekItem,
                    onCheckedChange = {
                        vm.toggleTaskChecked(task.id)
                    },
                    onDelete = {
                        vm.deleteTask(task.id) },
                    onClick = {navController.navigate(NavRoutes.About.createRoute(task.item))}
                )

            }
        }
    }
}

fun getFirstTwoWords(text: String): String{
    val words = text.trim().split(Regex("\\s+"))

    val firstWord= words.getOrNull(0) ?: ""
    val secondWord = words.getOrNull(1) ?: ""

    if(firstWord.length + secondWord.length > 25) {
        return text.take(25)
    }
    return "$firstWord $secondWord"
}
