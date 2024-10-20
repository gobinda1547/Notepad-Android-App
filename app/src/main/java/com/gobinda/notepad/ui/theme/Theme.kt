package com.gobinda.notepad.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SingleColorScheme = lightColorScheme(
    background = darkBackgroundColor100,
    onBackground = appTextColor100,
    primary = darkBackgroundColor100,
    onPrimary = appTextColor100,
    surface = darkBackgroundColor100,
    onSurface = appTextColor100,
    surfaceVariant = darkBackgroundColor50,
    onSurfaceVariant = appTextColor50
)

@Composable
fun NotepadTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = SingleColorScheme.background.toArgb()
            window.navigationBarColor = SingleColorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = SingleColorScheme,
        typography = Typography,
        content = content
    )
}