package com.example.chromasync.data.models

import androidx.compose.ui.text.font.FontFamily

enum class FontStyle(
    val displayName: String,
    val fontFamily: String,
    val composeFamily: FontFamily
) {
    DEFAULT(
        displayName = "Clean & Readable",
        fontFamily = "default",
        composeFamily = FontFamily.Default
    ),
    SERIF(
        displayName = "Classic & Elegant",
        fontFamily = "serif",
        composeFamily = FontFamily.Serif
    ),
    MONOSPACE(
        displayName = "Technical & Modern",
        fontFamily = "monospace",
        composeFamily = FontFamily.Monospace
    )
}