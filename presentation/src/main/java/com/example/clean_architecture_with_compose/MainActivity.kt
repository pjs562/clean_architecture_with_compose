package com.example.clean_architecture_with_compose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clean_architecture_with_compose.ui.home.HomeScreen
import com.example.clean_architecture_with_compose.ui.theme.Clean_architecture_with_composeTheme
import com.example.clean_architecture_with_compose.ui.web.WebViewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Clean_architecture_with_composeTheme {
                val navController = rememberNavController()
                NavHost(navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }

        composable("webview/{value}") { backStackEntry ->
            WebViewScreen(
                navController = navController,
                url = backStackEntry.arguments?.getString("value") ?: ""
            )
        }
    }
}