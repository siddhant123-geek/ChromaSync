package com.example.chromasync.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chromasync.utils.ThemeManager.toComposeColorScheme
import com.example.chromasync.utils.ThemeManager.toComposeShapes
import com.example.chromasync.utils.ThemeManager.toSimpleComposeTypography
import com.example.chromasync.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenViews(viewModel: ThemeViewModel= hiltViewModel()) {
    val currTheme by viewModel.currTheme.collectAsStateWithLifecycle()

    MaterialTheme(
        colorScheme = currTheme.toComposeColorScheme(),
        typography = currTheme.toSimpleComposeTypography(),
        shapes = currTheme.toComposeShapes()
    ) {
        Scaffold(
            topBar = {
                // AppBar demonstrating primary color and typography
                TopAppBar(
                    title = {
                        Text(
                            "ChromaSync Dashboard",
                            style = MaterialTheme.typography.titleLarge // Typography from current theme
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            content = {p->
                // show the items
                Text("For reference")
            }
        )
    }
}