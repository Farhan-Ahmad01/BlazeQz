package com.example.blazeqz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.blazeqz.presentation.navigation.NavGraph
import com.example.blazeqz.presentation.preview.PreviewScreen
import com.example.blazeqz.presentation.theme.BlazeQzTheme

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
