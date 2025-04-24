package com.example.todolistnavigation.navigation

import java.net.URLEncoder

sealed class NavRoutes(val route: String) {

    object Home : NavRoutes("home")
    object AddItem : NavRoutes("addItem")
    object About : NavRoutes("about/{index}"){
        fun createRoute(item: String) = "about/${URLEncoder.encode(item, "UTF-8")}"
    }
}