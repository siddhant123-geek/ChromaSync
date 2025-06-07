package com.example.chromasync.utils

import android.util.Log
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chromasync.data.models.CornerStyle
import com.example.chromasync.data.models.FontStyle
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.data.models.ColorOptions.PrimaryColorOption
import com.example.chromasync.data.models.ColorOptions.SecondaryColorOption
import com.example.chromasync.data.models.ColorOptions.SurfaceColorOption
import com.example.chromasync.data.models.ColorOptions.BackgroundColorOption
import com.example.chromasync.data.models.ColorOptions.PrimaryTextColorOption
import com.example.chromasync.data.models.ColorOptions.SecondaryTextColorOption

object ThemeManager {

    // a default theme to use in case there is no user defined theme
    fun getDefaultTheme(): ThemeProfile {
        return ThemeProfile(
            id = "default-theme",
            name = "Clean & Comfortable",
            primaryColor = PrimaryColorOption.MATERIAL_PURPLE.colorValue,
            secondaryColor = SecondaryColorOption.TEAL_ACCENT.colorValue,
            surfaceColor = SurfaceColorOption.SOFT_GRAY.colorValue,
            backgroundColor = BackgroundColorOption.LIGHT_CANVAS.colorValue,
            primaryTextColor = PrimaryTextColorOption.DEEP_BLACK.colorValue,
            secondaryTextColor = SecondaryTextColorOption.MEDIUM_GRAY.colorValue,
            cornerRadius = CornerStyle.STANDARD.radiusValue,
            fontFamily = FontStyle.DEFAULT.fontFamily,
            isActive = true,
            lastModified = 0L
        )
    }

    // get some pre-defined themes for easy usage
    fun getSampleThemes(): List<ThemeProfile> {
        return listOf(
            getDefaultTheme(),
            // Professional theme - blues and grays with clean lines
            ThemeProfile(
                id = "professional-theme",
                name = "Professional Blue",
                primaryColor = PrimaryColorOption.OCEAN_BLUE.colorValue,
                secondaryColor = SecondaryColorOption.INDIGO_COOL.colorValue,
                surfaceColor = SurfaceColorOption.WARM_CREAM.colorValue,
                backgroundColor = BackgroundColorOption.PEACH_CREAM.colorValue,
                primaryTextColor = PrimaryTextColorOption.CHARCOAL_GRAY.colorValue,
                secondaryTextColor = SecondaryTextColorOption.BLUE_GRAY.colorValue,
                cornerRadius = CornerStyle.MINIMAL.radiusValue,
                fontFamily = FontStyle.DEFAULT.fontFamily,
                isActive = false,
                lastModified = 0L
            ),

            // Warm and friendly theme - greens and warm colors
            ThemeProfile(
                id = "warm-theme",
                name = "Warm & Welcoming",
                primaryColor = PrimaryColorOption.FOREST_GREEN.colorValue,
                secondaryColor = SecondaryColorOption.AMBER_WARM.colorValue,
                surfaceColor = SurfaceColorOption.DARK_COLOR.colorValue,
                backgroundColor = BackgroundColorOption.ROSE_MIST.colorValue,
                primaryTextColor = PrimaryTextColorOption.SLATE_DARK.colorValue,
                secondaryTextColor = SecondaryTextColorOption.SOFT_GRAY.colorValue,
                cornerRadius = CornerStyle.ROUNDED.radiusValue,
                fontFamily = FontStyle.SERIF.fontFamily,
                isActive = false,
                lastModified = 0L
            )
        )
    }

    // extension function to convert the theme profile colors to compose colors
    fun ThemeProfile.toComposeColorScheme(): ColorScheme {
        return lightColorScheme(
            primary = Color(android.graphics.Color.parseColor(primaryColor)),
            secondary = Color(android.graphics.Color.parseColor(secondaryColor)),
            surface = Color(android.graphics.Color.parseColor(surfaceColor)),
            background = Color(android.graphics.Color.parseColor(backgroundColor)),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onSurface = Color(android.graphics.Color.parseColor(primaryTextColor)),
            onBackground = Color(android.graphics.Color.parseColor(primaryTextColor)),
            onSurfaceVariant = Color(android.graphics.Color.parseColor(secondaryTextColor))
        )
    }

    // extension function to convert the theme profile font styles to compose typography
    fun ThemeProfile.toSimpleComposeTypography(): Typography {
        Log.d("###", "toSimpleComposeTypography: fontFam " + fontFamily)
        val fontFamily = when (fontFamily) {
            "Serif" -> FontFamily.Serif
            "Monospace" -> FontFamily.Monospace
            else -> FontFamily.Default
        }

        Log.d("###", "toSimpleComposeTypography: font style " + fontFamily.toString())

        // Start with defaults and only modify what we need
        val base = Typography()

        return base.copy(
            // Main content text styles
            bodyLarge = base.bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = base.bodyMedium.copy(fontFamily = fontFamily),

            // Header text styles
            headlineLarge = base.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = base.headlineMedium.copy(fontFamily = fontFamily),

            // UI element text styles
            titleLarge = base.titleLarge.copy(fontFamily = fontFamily),
            labelLarge = base.labelLarge.copy(fontFamily = fontFamily)
        )
    }

    // extension function to convert the theme profile shapes to compose shapes
    fun ThemeProfile.toComposeShapes(): Shapes {
        return Shapes(
            extraSmall = RoundedCornerShape(cornerRadius.dp),
            small = RoundedCornerShape(cornerRadius.dp),
            medium = RoundedCornerShape((cornerRadius * 1.2f).dp),
            large = RoundedCornerShape((cornerRadius * 1.5f).dp),
            extraLarge = RoundedCornerShape((cornerRadius * 2f).dp)
        )
    }
}