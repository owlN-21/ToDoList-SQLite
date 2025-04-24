package com.example.todolistnavigation

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolistnavigation.features.AddItem
import com.example.todolistnavigation.features.InformationItem
import com.example.todolistnavigation.features.TodoUI
import com.example.todolistnavigation.navigation.NavRoutes
import com.example.todolistnavigation.ui.theme.mainColor


// фабрика
class UserViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(application) as T
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: TodoViewModel = viewModel(
                    it,
                    "UserViewModel",
                    UserViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                Main(viewModel)
            }
        }
    }
}



@Composable
fun Main(vm: TodoViewModel = viewModel()) {
    val navController = rememberNavController() // контроллер навигации

    val userList by vm.taskList.observeAsState(listOf())

    val userCheckTask by vm.checkedCount.observeAsState(listOf())
    val trueCount = userCheckTask.count { it == true }

    Column(Modifier.padding(20.dp))
    {
//        NavBar(navController = navController)
        MyToolbar(userList.size?:0, trueCount, navController)
        // Хост навигации
        NavHost(
            navController,
            startDestination = NavRoutes.Home.route
        ) {
            composable(NavRoutes.Home.route) { TodoUI(navController, vm) }

            composable(NavRoutes.AddItem.route) { AddItem(vm) }

            composable(
                route = "about/{item}",
                arguments = listOf(navArgument("item") { type = NavType.StringType })
            ) { entry ->
                val item = entry.arguments?.getString("item").orEmpty()
                InformationItem(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(totalTasks: Int, checkedTask: Int, navController: NavController){
    TopAppBar(
        title = { Text("To Do list",
                Modifier.clickable { navController.navigate(NavRoutes.Home.route) })},
        actions = {
            Text(
                text = "Total $totalTasks - "
            );
            Text(
                text = "Checked $checkedTask"
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mainColor,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}


