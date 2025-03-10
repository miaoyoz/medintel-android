package com.miaoyongzheng.medintel_android.ui.screen.welcome

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miaoyongzheng.myapplication.R
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {}
) {
    var enabled by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.3f,
        animationSpec = tween(durationMillis = 500)
    )
    LaunchedEffect(Unit) {
        enabled = true
        delay(1000)
        onNavigateToHome()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.welcome_background),
                sizeToIntrinsics = false,
                contentScale = ContentScale.FillBounds,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(80.dp)
                .alpha(alpha)
                .clip(RoundedCornerShape(50))
                .background(color = Color(0xFFFFFFFF))
                .shadow(
                    elevation = 12.dp,
                    spotColor = Color(0x1A090F47),
                    ambientColor = Color(0x1A090F47)
                )
                .padding(10.dp),
            painter = painterResource(R.drawable.welcome_logo),
            tint = Color(0xFF2F78FF),
            contentDescription = null
        )
        Text(
            modifier = Modifier.alpha(alpha),
            text = "医智",
            style = MaterialTheme.typography.displaySmall,
            fontStyle = FontStyle.Italic,
            color = Color.White
        )
    }
}