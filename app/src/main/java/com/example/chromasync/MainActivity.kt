package com.example.chromasync

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.chromasync.ui.ThemeNavHost
import com.example.chromasync.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        setupSplashScreen(splashScreen)

        setContent {
            ThemeNavHost()
        }
    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        // keeping the splash screen visible until theme is loaded
        splashScreen.setKeepOnScreenCondition {
            !themeViewModel.isThemeReady
        }
    }
}