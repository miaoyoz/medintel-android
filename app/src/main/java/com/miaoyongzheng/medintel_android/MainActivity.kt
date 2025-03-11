package com.miaoyongzheng.medintel_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miaoyongzheng.medintel_android.ui.screen.chatbot.ChatBotScreen
import com.miaoyongzheng.medintel_android.ui.screen.main.MainScreen
import com.miaoyongzheng.medintel_android.ui.screen.welcome.WelcomeScreen
import com.miaoyongzheng.medintel_android.ui.theme.AppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MyApp()
            }
        }
    }
}


object BeginRoute{
    const val WELCOME = "WelcomeScreen"
    const val MAIN = "MainScreen"
    const val CHATBOT = "ChatBotScreen"
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = BeginRoute.WELCOME,
    ) {
        composable(BeginRoute.WELCOME) {
            WelcomeScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateToHome = {
                    navController.navigate(route = BeginRoute.MAIN) {
                        launchSingleTop = true
                        popUpTo(BeginRoute.WELCOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(BeginRoute.MAIN) {
            MainScreen(modifier = Modifier.fillMaxSize(),
                onNavigateToBot = { navController.navigate(route = BeginRoute.CHATBOT) }
            )
        }
        composable(BeginRoute.CHATBOT) {
            ChatBotScreen()
        }
    }
}




