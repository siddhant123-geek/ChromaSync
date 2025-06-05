package com.example.chromasync.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.chromasync.R
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.ThemeManager
import com.example.chromasync.utils.ThemeManager.toComposeColorScheme
import com.example.chromasync.utils.ThemeManager.toComposeShapes
import com.example.chromasync.utils.ThemeManager.toSimpleComposeTypography
import com.example.chromasync.utils.UiState
import com.example.chromasync.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenViews(viewModel: ThemeViewModel= hiltViewModel(), onNextClick: (String)-> Unit) {
    val currTheme by viewModel.currTheme.collectAsStateWithLifecycle()
    Log.d("###", "HomeScreenViews: currTheme " + currTheme.id)
    var isThemeSelVis by remember { mutableStateOf(false) }

    val uiState by viewModel.currThemes.collectAsStateWithLifecycle()

    // Wrapper used to customize the app theme
    // adding my theme configs in this wrapper
    MaterialTheme(
        colorScheme = currTheme.toComposeColorScheme(),
        typography = currTheme.toSimpleComposeTypography(),
        shapes = currTheme.toComposeShapes(),
    ) {
        Scaffold(
            topBar = {
                // AppBar demonstrating primary color and typography
                TopAppBar(
                    title = {
                        Text(
                            "ChromaSync Home",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    actions = {
                        // Theme selector in app bar for easy switching
                        ThemeDropdownSelector(
                            currTheme = currTheme,
                            uiState = uiState,
                            onThemeSel = viewModel::updateAppTheme
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // Handle adding new theme
                        isThemeSelVis=true
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new theme"
                    )
                }
            },
            content = {p->
                CreateThemeDialog(
                    isVisible = isThemeSelVis,
                    onDismiss = {
                        isThemeSelVis = false
                    },
                    onSaveTheme = { newTheme ->
                        viewModel.addThemeToDb(newTheme)
                        isThemeSelVis = false
                    }
                )
                // show the items
                Column(modifier = Modifier.padding(horizontal = 10.dp,
                    vertical = p.calculateTopPadding())) {
                    Spacer(Modifier.height(8.dp))
                    SurfaceCard(currTheme)

                    Spacer(Modifier.height(8.dp))
                    TextCard(currTheme)

                    Button(
                        onClick = {
                            onNextClick.invoke("list")
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    ) {
                        Text("Next")
                    }
                }
            }
        )
    }
}

@Composable
fun SurfaceCard(currTheme: ThemeProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp,
            focusedElevation = 12.dp,
            hoveredElevation = 10.dp,
            draggedElevation = 16.dp,
            disabledElevation = 8.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Welcome to ChromaSync",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Current theme: ${currTheme.name}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Change the theme to see the magic",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun TextCard(currTheme: ThemeProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp,
            focusedElevation = 12.dp,
            hoveredElevation = 10.dp,
            draggedElevation = 16.dp,
            disabledElevation = 8.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Primary text color",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Secondary text color",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDropdownSelector(
    viewModel: ThemeViewModel = hiltViewModel(),
    currTheme: ThemeProfile,
    uiState: UiState<List<ThemeProfile>>,
    onThemeSel: (ThemeProfile) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            when(uiState) {
                is UiState.Success -> {
                    uiState.data.forEach { theme ->
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
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    // not allowing the curr theme deletion
                                    if (theme.id != currTheme.id) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_delete_24),
                                            contentDescription = "Delete theme",
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clickable {
                                                    viewModel.deleteTheme(theme.id)
                                                }
                                                .padding(2.dp)
                                        )
                                    }
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
                is UiState.Error -> {
                    IconButton(
                        onClick = {
                            // Allow user to retry loading themes
                            viewModel.refreshThemes()
                        }
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Retry loading themes",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    Log.d("###", "ThemeDropdownSelector: coming to error state in themes from db")
                    // error state
                }
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Log.d("###", "ThemeDropdownSelector: coming to loading state in themes from db")
                }
            }
        }
    }
}

@Composable
fun CreateThemeDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSaveTheme: (ThemeProfile) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    if (isVisible) {
        var themeName by remember { mutableStateOf("") }
        var primaryColor by remember { mutableStateOf("#6200EE") }
        var secondaryColor by remember { mutableStateOf("#03DAC6") }
        var surfaceColor by remember { mutableStateOf("#FFFFFF") }
        var backgroundColor by remember { mutableStateOf("#FFFFFB") }
        var primaryTextColor by remember { mutableStateOf("#000000") }
        var secondaryTextColor by remember { mutableStateOf("#757575") }
        var cornerRadius by remember { mutableStateOf(8f) }
        var fontFamily by remember { mutableStateOf("Default") }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Create New Theme") },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    // Theme Name
                    item {
                        OutlinedTextField(
                            value = themeName,
                            onValueChange = { themeName = it },
                            label = { Text("Theme Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Color Pickers
                    item { ColorPickerSection("Primary Color", primaryColor) { primaryColor = it } }
                    item { ColorPickerSection("Secondary Color", secondaryColor) { secondaryColor = it } }
                    item { ColorPickerSection("Surface Color", surfaceColor) { surfaceColor = it } }
                    item { ColorPickerSection("Background Color", backgroundColor) { backgroundColor = it } }
                    item { ColorPickerSection("Primary Text Color", primaryTextColor) { primaryTextColor = it } }
                    item { ColorPickerSection("Secondary Text Color", secondaryTextColor) { secondaryTextColor = it } }

                    // Corner Radius Options
                    item {
                        CornerRadiusSelector(
                            selectedRadius = cornerRadius,
                            onRadiusSelected = { cornerRadius = it }
                        )
                    }

                    // Font Family Options
                    item {
                        FontFamilySelector(
                            selectedFont = fontFamily,
                            onFontSelected = { fontFamily = it }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(themeName.isEmpty()) {
                            scope.launch {
                                Toast.makeText(context, "Please enter a valid theme name",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                        else {
                            val newTheme = ThemeProfile(
                                id = UUID.randomUUID().toString(),
                                name = themeName,
                                primaryColor = primaryColor,
                                secondaryColor = secondaryColor,
                                surfaceColor = surfaceColor,
                                backgroundColor = backgroundColor,
                                primaryTextColor = primaryTextColor,
                                secondaryTextColor = secondaryTextColor,
                                cornerRadius = cornerRadius,
                                fontFamily = fontFamily,
                                isActive = false,
                                lastModified = System.currentTimeMillis()
                            )
                            onSaveTheme(newTheme)
                            onDismiss()
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ColorPickerSection(
    label: String,
    currentColor: String,
    onColorChanged: (String) -> Unit
) {
    // State to control color picker dialog visibility
    var showColorPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // This creates a clickable color preview that feels more intuitive
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { showColorPicker = true }, // Opens picker on click
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Larger, more prominent color preview
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor(currentColor)),
                            shape = CircleShape // Round shape looks more modern
                        )
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            CircleShape
                        )
                )

                // Display current color value (read-only)
                Text(
                    text = currentColor.uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))

                // Visual indicator that this is clickable
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit color",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    // Color picker dialog - shows when user clicks the color section
    if (showColorPicker) {
        CircularColorPickerDialog(
            currentColor = currentColor,
            onColorSelected = { newColor ->
                onColorChanged(newColor)
                showColorPicker = false
            },
            onDismiss = { showColorPicker = false }
        )
    }
}

@Composable
fun CircularColorPickerDialog(
    currentColor: String,
    onColorSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    // We'll use HSV (Hue, Saturation, Value) color space for the picker
    // This is more intuitive for users than RGB
    var hue by remember { mutableStateOf(0f) }
    var saturation by remember { mutableStateOf(1f) }
    var brightness by remember { mutableStateOf(1f) }

    // Convert current color to HSV for initial values
    LaunchedEffect(currentColor) {
        val color = android.graphics.Color.parseColor(currentColor)
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(color, hsv)
        hue = hsv[0]
        saturation = hsv[1]
        brightness = hsv[2]
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Choose Color",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Color preview - shows the currently selected color
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = Color.hsv(hue, saturation, brightness),
                            shape = CircleShape
                        )
                        .border(
                            3.dp,
                            MaterialTheme.colorScheme.outline,
                            CircleShape
                        )
                )

                // Hue slider - controls the base color (red, green, blue, etc.)
                ColorSlider(
                    label = "Hue",
                    value = hue,
                    onValueChange = { hue = it },
                    valueRange = 0f..360f,
                    colors = listOf(
                        Color.Red, Color.Yellow, Color.Green,
                        Color.Cyan, Color.Blue, Color.Magenta, Color.Red
                    )
                )

                // Saturation slider - controls color intensity
                ColorSlider(
                    label = "Saturation",
                    value = saturation,
                    onValueChange = { saturation = it },
                    valueRange = 0f..1f,
                    colors = listOf(
                        Color.hsv(hue, 0f, brightness),
                        Color.hsv(hue, 1f, brightness)
                    )
                )

                // Brightness slider - controls how light/dark the color is
                ColorSlider(
                    label = "Brightness",
                    value = brightness,
                    onValueChange = { brightness = it },
                    valueRange = 0f..1f,
                    colors = listOf(
                        Color.hsv(hue, saturation, 0f),
                        Color.hsv(hue, saturation, 1f)
                    )
                )

                // Show the hex value
                Text(
                    text = String.format("#%06X",
                        (Color.hsv(hue, saturation, brightness).toArgb() and 0xFFFFFF)),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Monospace
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedColor = String.format("#%06X",
                        (Color.hsv(hue, saturation, brightness).toArgb() and 0xFFFFFF))
                    onColorSelected(selectedColor)
                }
            ) {
                Text("Select")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ColorSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    colors: List<Color>
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            // Custom colors for the slider track
            colors = SliderDefaults.colors(
                activeTrackColor = colors.last(),
                inactiveTrackColor = colors.first()
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CornerRadiusSelector(
    selectedRadius: Float,
    onRadiusSelected: (Float) -> Unit
) {
    Column {
        Text(text = "Corner Radius", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val radiusOptions = listOf(4f, 8f, 16f)
            val radiusLabels = listOf("S", "M", "L")

            radiusOptions.forEachIndexed { index, radius ->
                FilterChip(
                    onClick = { onRadiusSelected(radius) },
                    label = { Text("${radiusLabels[index]} (${radius.toInt()}dp)") },
                    selected = selectedRadius == radius
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontFamilySelector(
    selectedFont: String,
    onFontSelected: (String) -> Unit
) {
    Column {
        Text(text = "Font Family", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val fontOptions = listOf("Default", "Serif", "Monospace")

            fontOptions.forEach { font ->
                FilterChip(
                    onClick = { onFontSelected(font) },
                    label = { Text(font) },
                    selected = selectedFont == font
                )
            }
        }
    }
}