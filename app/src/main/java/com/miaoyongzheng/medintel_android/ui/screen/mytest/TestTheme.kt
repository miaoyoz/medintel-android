package com.miaoyongzheng.medintel_android.ui.screen.mytest


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.miaoyongzheng.medintel_android.ui.theme.AppTypography

private val NormalColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color.White

    )

private val VipColorScheme = lightColorScheme(
    primary = Color.Red,
    onPrimary = Color.Blue
)

@Composable
fun TestTheme(
    isVip: Boolean,
    content: @Composable () -> Unit,

) {

    val colorScheme = when (isVip) {
        true -> VipColorScheme
        else -> NormalColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

