package ir.heyatyab.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.heyatyab.presentation.utils.SnackBarController

fun YellowLightColors.toCustomColors() = CustomColors(
    primary = Primary,
    primaryVariant = PrimaryVariant,
    secondary = Secondary,
    background = Background,
    surface = Surface,
    cardInnerBG = CardInnerBG,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textHint = TextHint,
    divider = Divider,
    bottomNavigationBG = BottomNavigationBG,
    iconColor = IconColor,
    green = Green,
    greenLight = GreenLight,
    red = Red,
    redLight = RedLight,
    blue = Blue,
    yellow = Yellow
)

fun YellowDarkColors.toCustomColors() = CustomColors(
    primary = Primary,
    primaryVariant = PrimaryVariant,
    secondary = Secondary,
    background = Background,
    surface = Surface,
    cardInnerBG = CardInnerBG,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textHint = TextHint,
    divider = Divider,
    bottomNavigationBG = BottomNavigationBG,
    iconColor = IconColor,
    green = Green,
    greenLight = GreenLight,
    red = Red,
    redLight = RedLight,
    blue = Blue,
    yellow = Yellow
)

val LocalSnackbarController = staticCompositionLocalOf<SnackBarController> {
    SnackBarController()
}

val LocalCustomColors = staticCompositionLocalOf<CustomColors>{
    error("No CustomColors provided")
}

@Composable
fun HeyatYabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorTheme = if (darkTheme) YellowDarkColors().toCustomColors() else YellowLightColors().toCustomColors()

    CompositionLocalProvider(
        LocalCustomColors provides colorTheme ,
        LocalLayoutDirection provides LayoutDirection.Rtl ,

        ) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}