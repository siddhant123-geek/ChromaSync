package com.example.chromasync.data.models

object ColorOptions {

    enum class PrimaryColorOption(val displayName: String, val colorValue: String) {
        MATERIAL_PURPLE("Material Purple", "#6200EE"),  // Classic Material Design
        OCEAN_BLUE("Ocean Blue", "#0277BD"),           // Professional, trustworthy
        FOREST_GREEN("Forest Green", "#388E3C")        // Natural, calming
    }

    /**
     * Secondary Color Options - These complement the primary color for accents and highlights
     * Each works well with any primary color choice
     */
    enum class SecondaryColorOption(val displayName: String, val colorValue: String) {
        TEAL_ACCENT("Teal Accent", "#03DAC5"),         // Modern, vibrant
        AMBER_WARM("Amber Warm", "#FF8F00"),           // Energetic, friendly
        INDIGO_COOL("Indigo Cool", "#3F51B5")          // Sophisticated, calm
    }

    /**
     * Surface Color Options - These are for cards, dialogs, and elevated components
     * Range from pure white to subtle grays for different visual weights
     */
    enum class SurfaceColorOption(val displayName: String, val colorValue: String) {
        PURE_WHITE("Pure White", "#FFFFFF"),           // Clean, minimal
        SOFT_GRAY("Soft Gray", "#F5F5F5"),            // Subtle, gentle
        WARM_CREAM("Warm Cream", "#FFF8E1")           // Cozy, inviting
    }

    /**
     * Background Color Options - The main canvas color behind all content
     * These provide different "moods" for the overall app feeling
     */
    enum class BackgroundColorOption(val displayName: String, val colorValue: String) {
        LIGHT_CANVAS("Light Canvas", "#FFFFFB"),       // Slightly off-white, easier on eyes
        COOL_WHITE("Cool White", "#F8F9FA"),          // Clean, modern feeling
        WARM_WHITE("Warm White", "#FFF9C4")           // Comfortable, welcoming
    }

    /**
     * Primary Text Color Options - Main text that appears on backgrounds
     * Range from pure black to softer grays for different reading experiences
     */
    enum class PrimaryTextColorOption(val displayName: String, val colorValue: String) {
        DEEP_BLACK("Deep Black", "#1C1B1F"),          // Maximum contrast, sharp
        CHARCOAL_GRAY("Charcoal Gray", "#2E2E2E"),    // Softer than black, still strong
        SLATE_DARK("Slate Dark", "#455A64")           // Gentle, sophisticated
    }

    /**
     * Secondary Text Color Options - For less important text, captions, hints
     * These provide hierarchy while maintaining readability
     */
    enum class SecondaryTextColorOption(val displayName: String, val colorValue: String) {
        MEDIUM_GRAY("Medium Gray", "#49454F"),        // Standard Material Design
        SOFT_GRAY("Soft Gray", "#6C6C6C"),           // Gentle, unobtrusive
        BLUE_GRAY("Blue Gray", "#607D8B")            // Slightly colored, interesting
    }
}