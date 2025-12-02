package ir.heyatyab.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.heyatyab.R

val CustomFontBold = FontFamily(Font(R.font.iransans_bold))
val CustomFontMedium = FontFamily(Font(R.font.iransans_medium))
val CustomFontRegular = FontFamily(Font(R.font.iransans_regular))

val Typography = Typography(
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontFamily = CustomFontBold,
        fontWeight = FontWeight(700)
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = CustomFontBold,
        fontWeight = FontWeight(700)
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = CustomFontBold,
        fontWeight = FontWeight(700)
    ),

    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontFamily = CustomFontMedium,
        fontWeight = FontWeight(500)
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = CustomFontMedium,
        fontWeight = FontWeight(500)
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = CustomFontMedium,
        fontWeight = FontWeight(500)
    ),

    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontFamily = CustomFontRegular,
        fontWeight = FontWeight(400)
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontFamily = CustomFontRegular,
        fontWeight = FontWeight(400)
    ),
    labelSmall = TextStyle(
        fontSize = 10.sp,
        fontFamily = CustomFontRegular,
        fontWeight = FontWeight(400)
    )
)