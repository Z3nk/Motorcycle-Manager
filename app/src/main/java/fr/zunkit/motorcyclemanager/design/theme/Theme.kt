package fr.zunkit.motorcyclemanager.design.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF78BAEF),
    onPrimary = Color.Black,

    primaryContainer = Color(0xFF42A5F5),
    onPrimaryContainer = Color.Black,

    secondary = Color(0xFFEF5350),
    onSecondary = Color.White,

    background = Color(0xFF0A0E17),
    onBackground = Color.White,

    surface = Color(0xFF1A2332),
    onSurface = Color.White,

    surfaceVariant = Color(0xFF2D3748),
    onSurfaceVariant = Color(0xFFB0BEC5),

    outline = Color(0xFFBAC3D2),
    outlineVariant = Color(0xFF484F58),

    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun MotorcycleManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}