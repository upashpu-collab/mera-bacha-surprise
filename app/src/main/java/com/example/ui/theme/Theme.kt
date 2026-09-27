package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RomanticLightColorScheme = lightColorScheme(
  primary = RosePrimary,
  onPrimary = PureWhite,
  primaryContainer = PetalPink,
  onPrimaryContainer = TextPrimaryDark,
  secondary = RomanticPink,
  onSecondary = PureWhite,
  secondaryContainer = BlushPink,
  onSecondaryContainer = TextSecondaryDark,
  tertiary = RomanticGold,
  onTertiary = PureWhite,
  tertiaryContainer = RomanticGoldLight,
  onTertiaryContainer = TextPrimaryDark,
  background = CreamBackground,
  onBackground = TextPrimaryDark,
  surface = PureWhite,
  onSurface = TextPrimaryDark,
  surfaceVariant = CreamSurface,
  onSurfaceVariant = TextSecondaryDark,
  outline = CardBorderPink
)

private val RomanticDarkColorScheme = darkColorScheme(
  primary = SoftPink,
  onPrimary = TextPrimaryDark,
  primaryContainer = RoseDark,
  onPrimaryContainer = BlushPink,
  secondary = PetalPink,
  onSecondary = TextPrimaryDark,
  tertiary = RomanticGoldLight,
  onTertiary = TextPrimaryDark,
  background = Color(0xFF1E0E14),
  onBackground = BlushPink,
  surface = Color(0xFF2C1620),
  onSurface = BlushPink,
  surfaceVariant = Color(0xFF3D1F2D),
  onSurfaceVariant = PetalPink,
  outline = Color(0xFF5D2E42)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Forced false to maintain the romantic birthday ambiance
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) RomanticDarkColorScheme else RomanticLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
