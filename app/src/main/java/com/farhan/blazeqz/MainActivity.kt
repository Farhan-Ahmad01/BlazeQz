package com.farhan.blazeqz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.farhan.blazeqz.presentation.navigation.NavGraph
import com.farhan.blazeqz.presentation.theme.BlazeQzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            BlazeQzTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                )
            }
        }
    }
}
