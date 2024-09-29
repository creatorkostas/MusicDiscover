package com.musicdiscover.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val AuraColorScheme = lightColorScheme(

)


private val AuraSoftColorScheme = darkColorScheme(
//    primary = aura_purple_soft,
//    secondary = aura_green_soft,
//    tertiary = aura_orange_soft,
//    background = aura_black_soft,

//    surface = aura_white_soft, //forground
//    primary = Purple40,
//    primary = Purple40,
//    primary = Purple40,
//    primary = Purple40,
//    primary = Purple40,
//    primary = Purple40,
    primary = aura_purple_soft,
    onPrimary = aura_black_soft,

    secondary = aura_green_soft,
    onSecondary = aura_orange_soft,

    tertiary = aura_orange_soft,
    background = aura_black_soft,
    surface = aura_black_soft,

    outline = aura_green_soft, //text filed round round
    outlineVariant = aura_green_soft,
    onSurfaceVariant = aura_purple_soft, //text filed in

//    onSurface = aura_orange_soft,
//    primaryContainer = aura_orange_soft,
//    onPrimaryContainer = aura_orange_soft,
//    inversePrimary = aura_orange_soft,
//    secondaryContainer = aura_orange_soft,
//    onSecondaryContainer = aura_orange_soft,
//    onTertiary = aura_orange_soft,
//    tertiaryContainer = aura_orange_soft,
//    onTertiaryContainer = aura_orange_soft,
//    onBackground = aura_orange_soft,
//    surfaceVariant = aura_orange_soft,
//    surfaceTint = aura_purple_soft,
//    inverseSurface = aura_purple_soft,
//    inverseOnSurface = aura_purple_soft,
//    error = aura_purple_soft,
//    onError = aura_purple_soft,
//    errorContainer = aura_purple_soft,
//    onErrorContainer = aura_purple_soft,
//    scrim = aura_purple_soft,
//    surfaceBright = aura_purple_soft,
//    surfaceContainer = aura_purple_soft,
//    surfaceContainerHigh = aura_purple_soft,
//    surfaceContainerHighest = aura_purple_soft,
//    surfaceContainerLow = aura_purple_soft,
//    surfaceContainerLowest = aura_purple_soft,
//    surfaceDim = aura_purple_soft
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

//    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

)

@Composable
fun MusicDiscoverTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    theme: String? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        (theme == null || theme == "Dynamic" || theme == "null" || theme == "") && dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        theme == "Light" -> LightColorScheme
        theme == "Dark" -> DarkColorScheme

        theme == null  || theme == "System" -> {
                if (darkTheme) DarkColorScheme else LightColorScheme
        }
        else -> lightColorScheme()
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}