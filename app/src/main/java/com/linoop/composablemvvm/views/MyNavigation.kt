@file:OptIn(DelicateCoroutinesApi::class)

package com.linoop.composablemvvm.views

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.linoop.composablemvvm.MainActivity
import com.linoop.composablemvvm.viewmodels.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyNavigation(viewModel: MainViewModel, activity: MainActivity) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_view") {
        //viewModel.navController = navController
        composable(route = "login_view") {
            Login(viewModel = viewModel, navController = navController)
        }
        composable(route = "home_view/{token}", arguments = listOf(navArgument("token") {
            type = NavType.StringType
            defaultValue = "Invalid token"
            nullable = true
        })) {
            HomePage(token = it.arguments?.getString("token"))
        }

        activity.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.response.collectLatest {
                if (it.isNotEmpty()) {
                    navController.navigate("home_view/$it")
                }
            }
        }
    }
}