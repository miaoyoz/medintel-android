package com.miaoyongzheng.medintel_android.ui.screen.main.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.miaoyongzheng.myapplication.R

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()
    Column(
        modifier = modifier
            .background(Color(0xFFF7F9FB))
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTop(
            modifier = Modifier.fillMaxWidth(),
            avatar = state.avatar,
            username = state.name,
            searchInfo = state.searchInfo,
            hasNotification = state.hasNotification
        )
        CommonFunction(
            onClickIndex = {
                Log.d("miaoyoz1", "HomeScreen: $it")
            }
        )
    }
}


data class CommonDetail(
    val id: Int,
    val name: String,
    val icon: Int,
    val colors: List<Color>
)

val commonList = listOf(
    CommonDetail(
        id = 1,
        name = "挂号",
        icon = R.drawable.ecg_heart,
        colors = listOf(Color(0xFFFF9598), Color(0xFFFF70A7))
    ),
    CommonDetail(
        id = 2,
        name = "挂号",
        icon = R.drawable.ecg_heart,
        colors = listOf(Color(0xFF19E5A5), Color(0xFF15BD92))
    ),
    CommonDetail(
        id = 3,
        name = "挂号",
        icon = R.drawable.ecg_heart,
        colors = listOf(Color(0xFFFFC06F), Color(0xFFFF793A))
    ),
    CommonDetail(
        id = 4,
        name = "挂号",
        icon = R.drawable.ecg_heart,
        colors = listOf(Color(0xFF4DB7FF), Color(0xFF3E7DFF))
    )
)

@Preview(showBackground = true)
@Composable
fun CommonFunction(
    modifier: Modifier = Modifier,
    onClickIndex: (Int) -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(commonList) {
            CommonItem(
                name = it.name,
                icon = it.icon,
                colors = it.colors,
                onClick = { onClickIndex(it.id) }
            )
        }
    }
}

@Composable
fun CommonItem(
    modifier: Modifier = Modifier,
    name: String = "挂号",
    icon: Int = R.drawable.ecg_heart,
    colors: List<Color> = listOf(Color(0xFFFF9598), Color(0xFFFF70A7)),
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.linearGradient(colors)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFA6AFB6)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeTop(
    modifier: Modifier = Modifier,
    avatar: String? = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRESdc7plAfA1hqGf-WoAs3NQfCzvIQ9VQMdA&s",
    username: String = "缪永正",
    searchInfo: String = "感冒吃什么药能快速好？",
    hasNotification: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
                .background(Brush.linearGradient(listOf(Color(0xFF4157FF), Color(0xFF3D50E7))))
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawCircle(
                        color = Color(0xFF4C5FF0),
                        radius = 412f,
                        center = Offset(0f, 475f)
                    )
                })
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(42.dp),
                    model = avatar,
                    contentDescription = null,
                )
                Spacer(Modifier.weight(1f))
                BadgedBox(
                    badge = {
                        if (hasNotification) {
                            Badge()
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        painter = painterResource(R.drawable.notifications),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Spacer(Modifier.width(20.dp))
                Icon(
                    painter = painterResource(R.drawable.archive),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Text(
                text = "hello，${username}",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "欢迎来到医智，你的智能医疗App",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
            Row(
                modifier = Modifier
                    .shadow(
                        elevation = 14.dp,
                        spotColor = Color(0x12000000),
                        ambientColor = Color(0x12000000)
                    )
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(50))
                    .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color(0x990F4773)
                )
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = searchInfo,
                    color = Color(0x990F4773),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}