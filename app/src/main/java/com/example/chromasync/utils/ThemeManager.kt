package com.example.chromasync.utils

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chromasync.data.models.ColorOptions
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

    fun getDefaultTheme(): ThemeProfile {
        return ThemeProfile(
            id = "default-theme",
            name = "Clean & Comfortable",
            primaryColor = ColorOptions.PrimaryColorOption.MATERIAL_PURPLE.colorValue,
            secondaryColor = ColorOptions.SecondaryColorOption.TEAL_ACCENT.colorValue,
            surfaceColor = ColorOptions.SurfaceColorOption.PURE_WHITE.colorValue,
            backgroundColor = ColorOptions.BackgroundColorOption.LIGHT_CANVAS.colorValue,
            primaryTextColor = ColorOptions.PrimaryTextColorOption.DEEP_BLACK.colorValue,
            secondaryTextColor = ColorOptions.SecondaryTextColorOption.MEDIUM_GRAY.colorValue,
            cornerRadius = CornerStyle.STANDARD.radiusValue,
            fontFamily = FontStyle.DEFAULT.fontFamily,
            isActive = true,
            lastModified = System.currentTimeMillis()
        )
    }

    fun getSampleThemes(): List<ThemeProfile> {
        return listOf(
            getDefaultTheme(),
            // Professional theme - blues and grays with clean lines
            ThemeProfile(
                id = "professional-theme",
                name = "Professional Blue",
                primaryColor = ColorOptions.PrimaryColorOption.OCEAN_BLUE.colorValue,
                secondaryColor = ColorOptions.SecondaryColorOption.INDIGO_COOL.colorValue,
                surfaceColor = ColorOptions.SurfaceColorOption.SOFT_GRAY.colorValue,
                backgroundColor = ColorOptions.BackgroundColorOption.COOL_WHITE.colorValue,
                primaryTextColor = ColorOptions.PrimaryTextColorOption.CHARCOAL_GRAY.colorValue,
                secondaryTextColor = ColorOptions.SecondaryTextColorOption.BLUE_GRAY.colorValue,
                cornerRadius = CornerStyle.MINIMAL.radiusValue,
                fontFamily = FontStyle.DEFAULT.fontFamily,
                isActive = false,
                lastModified = System.currentTimeMillis()
            ),

            // Warm and friendly theme - greens and warm colors
            ThemeProfile(
                id = "warm-theme",
                name = "Warm & Welcoming",
                primaryColor = ColorOptions.PrimaryColorOption.FOREST_GREEN.colorValue,
                secondaryColor = ColorOptions.SecondaryColorOption.AMBER_WARM.colorValue,
                surfaceColor = ColorOptions.SurfaceColorOption.WARM_CREAM.colorValue,
                backgroundColor = ColorOptions.BackgroundColorOption.WARM_WHITE.colorValue,
                primaryTextColor = ColorOptions.PrimaryTextColorOption.SLATE_DARK.colorValue,
                secondaryTextColor = ColorOptions.SecondaryTextColorOption.SOFT_GRAY.colorValue,
                cornerRadius = CornerStyle.ROUNDED.radiusValue,
                fontFamily = FontStyle.SERIF.fontFamily,
                isActive = false,
                lastModified = System.currentTimeMillis()
            )
        )
    }

    // Get all available options for each attribute
    fun getPrimaryColorOptions(): List<ColorOptions.PrimaryColorOption> = ColorOptions.PrimaryColorOption.values().toList()
    fun getSecondaryColorOptions(): List<ColorOptions.SecondaryColorOption> = ColorOptions.SecondaryColorOption.values().toList()
    fun getSurfaceColorOptions(): List<ColorOptions.SurfaceColorOption> = ColorOptions.SurfaceColorOption.values().toList()
    fun getBackgroundColorOptions(): List<ColorOptions.BackgroundColorOption> = ColorOptions.BackgroundColorOption.values().toList()
    fun getPrimaryTextColorOptions(): List<ColorOptions.PrimaryTextColorOption> = ColorOptions.PrimaryTextColorOption.values().toList()
    fun getSecondaryTextColorOptions(): List<ColorOptions.SecondaryTextColorOption> = ColorOptions.SecondaryTextColorOption.values().toList()
    fun getCornerRadiusOptions(): List<CornerStyle> = CornerStyle.values().toList()
    fun getFontFamilyOptions(): List<FontStyle> = FontStyle.values().toList()

    /**
     * Helper functions to find the current selection for each attribute
     * These are useful when editing an existing theme profile
     */
    fun findPrimaryColorOption(colorValue: String): PrimaryColorOption? {
        return PrimaryColorOption.values().find { it.colorValue == colorValue }
    }

    fun findSecondaryColorOption(colorValue: String): SecondaryColorOption? {
        return SecondaryColorOption.values().find { it.colorValue == colorValue }
    }

    fun findSurfaceColorOption(colorValue: String): SurfaceColorOption? {
        return SurfaceColorOption.values().find { it.colorValue == colorValue }
    }

    fun findBackgroundColorOption(colorValue: String): BackgroundColorOption? {
        return BackgroundColorOption.values().find { it.colorValue == colorValue }
    }

    fun findPrimaryTextColorOption(colorValue: String): PrimaryTextColorOption? {
        return PrimaryTextColorOption.values().find { it.colorValue == colorValue }
    }

    fun findSecondaryTextColorOption(colorValue: String): SecondaryTextColorOption? {
        return SecondaryTextColorOption.values().find { it.colorValue == colorValue }
    }

    fun findCornerRadiusOption(radiusValue: Float): CornerStyle? {
        return CornerStyle.values().find { it.radiusValue == radiusValue }
    }

    fun findFontFamilyOption(fontValue: String): FontStyle? {
        return FontStyle.values().find { it.fontFamily == fontValue }
    }

    /**
     * Creates a description of the complete theme for preview purposes
     * This helps users understand what their selections will look like together
     */
    fun getThemeDescription(profile: ThemeProfile): String {
        val primaryColorName = findPrimaryColorOption(profile.primaryColor)?.displayName ?: "Unknown"
        val cornerStyleName = findCornerRadiusOption(profile.cornerRadius)?.displayName ?: "Unknown"
        val fontName = findFontFamilyOption(profile.fontFamily)?.displayName ?: "Unknown"

        return "$primaryColorName primary color, $cornerStyleName corners, $fontName font"
    }

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

    fun ThemeProfile.toSimpleComposeTypography(): Typography {
        val fontFamily = when (fontFamily) {
            "serif" -> FontFamily.Serif
            "monospace" -> FontFamily.Monospace
            else -> FontFamily.Default
        }

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