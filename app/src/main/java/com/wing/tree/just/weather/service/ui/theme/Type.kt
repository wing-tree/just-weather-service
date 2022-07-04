package com.wing.tree.just.weather.service.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wing.tree.just.weather.service.R

val NotoSerifKorean = FontFamily(
    Font(R.font.noto_serif_korean_black, weight = FontWeight.Black),
    Font(R.font.noto_serif_korean_bold, weight = FontWeight.Bold),
    Font(R.font.noto_serif_korean_extra_light, weight = FontWeight.ExtraLight),
    Font(R.font.noto_serif_korean_medium, weight = FontWeight.Medium),
    Font(R.font.noto_serif_korean_regular, weight = FontWeight.Normal),
    Font(R.font.noto_serif_korean_semi_bold, weight = FontWeight.SemiBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = NotoSerifKorean,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)