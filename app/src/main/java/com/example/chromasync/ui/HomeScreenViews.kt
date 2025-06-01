package com.example.chromasync.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.ThemeManager
import com.example.chromasync.utils.ThemeManager.toComposeColorScheme
import com.example.chromasync.utils.ThemeManager.toComposeShapes
import com.example.chromasync.utils.ThemeManager.toSimpleComposeTypography
import com.example.chromasync.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenViews(viewModel: ThemeViewModel= hiltViewModel()) {
    val currTheme by viewModel.currTheme.collectAsStateWithLifecycle()

    // Wrapper used to customize the app theme
    // adding my theme configs in this wrapper
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
                            "ChromaSync Home",
                            style = MaterialTheme.typography.titleLarge // Typography from current theme
                        )
                    },
                    actions = {
                        // Theme selector in app bar for easy switching
                        ThemeDropdownSelector(
                            currTheme = currTheme,
                            themes = ThemeManager.getSampleThemes(),
                            onThemeSel = viewModel::appTheme
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDropdownSelector(
    currTheme: ThemeProfile,
    themes: List<ThemeProfile>,
    onThemeSel: (ThemeProfile) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box{
        IconButton(
            onClick = {
                Log.d("###", "ThemeDropdownSelector: curr ex state " + expanded)
                expanded = !expanded },
        ) {
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = "Select Theme",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                Log.d("###", "ThemeDropdownSelector: coming inside dismissReq")
                expanded = false },
            offset = DpOffset(x = 0.dp, y = (-8).dp),
            properties = PopupProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                clippingEnabled = false
            )
        ) {
            themes.forEach { theme ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(
                                        Color(android.graphics.Color.parseColor(theme.primaryColor)),
                                        CircleShape
                                    )
                            )
                            Text(
                                text = theme.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    onClick = {
                        onThemeSel(theme)
                        Log.d("###", "ThemeDropdownSelector: expanded is made false")
                        expanded = false
                    }
                )
            }
        }
    }
}