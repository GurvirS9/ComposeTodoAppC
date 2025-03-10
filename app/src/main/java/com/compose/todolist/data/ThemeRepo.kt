package com.compose.todolist.data

data class ThemeOption(
    val id: String,
    val name: String,
    val isDark: Boolean,
    val colors: Map<String, String>
)

object ThemeRepo {
    val BWR = ThemeOption(
        id = "bwr",
        name = "BWR",
        isDark = false,
        colors = mapOf(
            "appBackground" to "#D9D9D9",
            "headerText" to "#303030",
            "iconTint" to "#303030",
            "cardBackground" to "#C8C8C8",
            "cardText" to "#303030",
            "checkboxBackground" to "#303030",
            "checkboxIcon" to "#D9D9D9",
            "fabBackground" to "#D71921",
            "fabIcon" to "#D9D9D9",
            "counterBackground" to "#EFEFEF",
            "counterText" to "#303030"
        )
    )

    val Cozy = ThemeOption(
        id = "cozy",
        name = "Cozy",
        isDark = false,
        colors = mapOf(
            "appBackground" to "#FFEEDD",
            "headerText" to "#303030",
            "iconTint" to "#303030",
            "cardBackground" to "#FCECD0",
            "cardText" to "#303030",
            "checkboxBackground" to "#303030",
            "checkboxIcon" to "#FFEEDD",
            "fabBackground" to "#D2691E",
            "fabIcon" to "#FFEEDD",
            "counterBackground" to "#DEB887",
            "counterText" to "#666666"
        )
    )

    val Snowy = ThemeOption(
        id = "snowy",
        name = "Snowy",
        isDark = false,
        colors = mapOf(
            "appBackground" to "#E0EDFF",
            "headerText" to "#303030",
            "iconTint" to "#303030",
            "cardBackground" to "#D1EAFE",
            "cardText" to "#303030",
            "checkboxBackground" to "#303030",
            "checkboxIcon" to "#E0EDFF",
            "fabBackground" to "#86ADD8",
            "fabIcon" to "#E0EDFF",
            "counterBackground" to "#D1EAFE",
            "counterText" to "#666666"
        )
    )

    val DejaVu = ThemeOption(
        id = "dejavu",
        name = "Deja Vu",
        isDark = false,
        colors = mapOf(
            "appBackground" to "#ECE0E8",
            "headerText" to "#303030",
            "iconTint" to "#303030",
            "cardBackground" to "#E1CCD2",
            "cardText" to "#303030",
            "checkboxBackground" to "#303030",
            "checkboxIcon" to "#ECE0E8",
            "fabBackground" to "#AE92B9",
            "fabIcon" to "#D9D9D9",
            "counterBackground" to "#D4C1C6",
            "counterText" to "#666666"
        )
    )

    val Ethereal = ThemeOption(
        id = "ethereal",
        name = "Ethereal",
        isDark = false,
        colors = mapOf(
            "appBackground" to "#FAF3F0",
            "headerText" to "#303030",
            "iconTint" to "#303030",
            "cardBackground" to "#E2FFE6",
            "cardText" to "#303030",
            "checkboxBackground" to "#303030",
            "checkboxIcon" to "#FFFFFF",
            "fabBackground" to "#A3C4E4",
            "fabIcon" to "#666666",
            "counterBackground" to "#E5D4ED",
            "counterText" to "#666666"
        )
    )

    val DarkBWR = ThemeOption(
        id = "dark_bwr",
        name = "Dark BWR",
        isDark = true,
        colors = mapOf(
            "appBackground" to "#1E1E20",
            "headerText" to "#A1A1A1",
            "iconTint" to "#A1A1A1",
            "cardBackground" to "#27292F",
            "cardText" to "#A1A1A1",
            "checkboxBackground" to "#A1A1A1",
            "checkboxIcon" to "#1E1E20",
            "fabBackground" to "#D71921",
            "fabIcon" to "#FFFFFF",
            "counterBackground" to "#27292F",
            "counterText" to "#A1A1A1"
        )
    )

    val Obsidian = ThemeOption(
        id = "obsidian",
        name = "Obsidian",
        isDark = true,
        colors = mapOf(
            "appBackground" to "#151515",
            "headerText" to "#E5E7EB",
            "iconTint" to "#E5E7EB",
            "cardBackground" to "#1F2937",
            "cardText" to "#E5E7EB",
            "checkboxBackground" to "#E5E7EB",
            "checkboxIcon" to "#151515",
            "fabBackground" to "#374151",
            "fabIcon" to "#D9D9D9",
            "counterBackground" to "#374151",
            "counterText" to "#D9D9D9"
        )
    )

    val Phantom = ThemeOption(
        id = "phantom",
        name = "Phantom",
        isDark = true,
        colors = mapOf(
            "appBackground" to "#0F0F0F",
            "headerText" to "#E5E7EB",
            "iconTint" to "#E5E7EB",
            "cardBackground" to "#1A1A1A",
            "cardText" to "#E5E7EB",
            "checkboxBackground" to "#E5E7EB",
            "checkboxIcon" to "#0F0F0F",
            "fabBackground" to "#00B4D8",
            "fabIcon" to "#E5E7EB",
            "counterBackground" to "#404040",
            "counterText" to "#E5E7EB"
        )
    )

    val Eclipse = ThemeOption(
        id = "eclipse",
        name = "Eclipse",
        isDark = true,
        colors = mapOf(
            "appBackground" to "#0A0A0F",
            "headerText" to "#FFF8E7",
            "iconTint" to "#FFF8E7",
            "cardBackground" to "#1E1E2E",
            "cardText" to "#FFF8E7",
            "checkboxBackground" to "#FFF8E7",
            "checkboxIcon" to "#1E1E2E",
            "fabBackground" to "#1E1E2E",
            "fabIcon" to "#FFF8E7",
            "counterBackground" to "#1E1E2E",
            "counterText" to "#FFF8E7"
        )
    )

    val AMOLED = ThemeOption(
        id = "amoled",
        name = "AMOLED",
        isDark = true,
        colors = mapOf(
            "appBackground" to "#000000",
            "headerText" to "#E9E9E9",
            "iconTint" to "#E9E9E9",
            "cardBackground" to "#151515",
            "cardText" to "#E9E9E9",
            "checkboxBackground" to "#E9E9E9",
            "checkboxIcon" to "#151515",
            "fabBackground" to "#E9E9E9",
            "fabIcon" to "#151515",
            "counterBackground" to "#E9E9E9",
            "counterText" to "#151515"
        )
    )

    // lists for easier access
    val lightThemes = listOf(BWR, Cozy, Snowy, DejaVu, Ethereal)
    val darkThemes = listOf(DarkBWR, Obsidian, Phantom, Eclipse, AMOLED)

    fun getAllThemes() = lightThemes + darkThemes
    fun getThemeById(id: String) = getAllThemes().find { it.id == id }

}