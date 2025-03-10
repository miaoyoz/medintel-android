package com.miaoyongzheng.medintel_android.ui.screen.main

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miaoyongzheng.medintel_android.ui.component.Toast
import com.miaoyongzheng.medintel_android.ui.screen.main.community.CommunityScreen
import com.miaoyongzheng.medintel_android.ui.screen.main.home.HomeScreen
import com.miaoyongzheng.medintel_android.ui.screen.main.message.MessageScreen
import com.miaoyongzheng.medintel_android.ui.screen.main.profile.ProfileScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
sealed class Main {
    @Serializable
    data object Home : Main()

    @Serializable
    data object Community : Main()

    @Serializable
    data object Message : Main()

    @Serializable
    data object Profile : Main()
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToBot: () -> Unit = {}
) {
    val navController = rememberNavController()
    //是否展示toast
    var showToast by remember { mutableStateOf(false) }
    //toast动画触发器
    var showAnimation by remember { mutableStateOf(false) }
    //按返回键的时间
    var backPressTime by remember { mutableLongStateOf(0L) }
    //用来退出程序用的activity
    val activity = LocalActivity.current
    //toast透明度变化动画
    val toastAlpha by animateFloatAsState(
        targetValue = if (showAnimation) 0.7f else 0f, animationSpec = tween(durationMillis = 500)
    )
    //当前导航的id
    var selectedId by rememberSaveable  { mutableIntStateOf(1) }

    //返回键监听
    BackHandler(enabled = true) {
        //如果当前不是Home，则会返回到Home
        val currentRoute = navController.currentDestination?.route
        if (selectedId != 1) {
            navController.navigate(Main.Home) {
                if (currentRoute != null) popUpTo(currentRoute) {
                    inclusive = true
                }
            }
            selectedId = 1
        } else {
            //误触判断
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressTime > 2000) {
                showToast = true
                showAnimation = true
                backPressTime = currentTime
            } else {
                Log.d("miaoyoz1", "MainScreen: $activity")
                activity?.finish()
            }
        }
    }

    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        MainBottomBar(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            selectedId = selectedId,
            onChangeId = {
                if (it != 3) {
                    selectedId = it
                }
                val currentRoute = navController.currentDestination?.route
                when (it) {
                    1 -> navController.navigate(Main.Home) {
                        if (currentRoute != null) popUpTo(currentRoute) {
                            inclusive = true
                        }
                    }

                    2 -> navController.navigate(Main.Community) {
                        if (currentRoute != null) popUpTo(currentRoute) {
                            inclusive = true
                        }
                    }

                    3 -> onNavigateToBot()

                    4 -> navController.navigate(Main.Message) {
                        if (currentRoute != null) popUpTo(currentRoute) {
                            inclusive = true
                        }
                    }

                    5 -> navController.navigate(Main.Profile) {
                        if (currentRoute != null) popUpTo(currentRoute) {
                            inclusive = true
                        }
                    }

                }
            })
    }) {
        Box(
            modifier = Modifier.padding(it), contentAlignment = Alignment.Center
        ) {
            Column(modifier = modifier.fillMaxSize()) {
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = Main.Home,
                ) {
                    composable<Main.Home> {
                        HomeScreen()
                    }
                    composable<Main.Community> {
                        CommunityScreen()
                    }

                    composable<Main.Message> {
                        MessageScreen()
                    }
                    composable<Main.Profile> {
                        ProfileScreen()
                    }
                }
            }
            if (showToast) {
                Toast(
                    modifier = Modifier.alpha(toastAlpha), text = "再按一次退出医智"
                )
                // 2秒后自动关闭对话框
                LaunchedEffect(showToast) {
                    delay(1500)
                    showAnimation = false
                    delay(500)
                    showToast = false
                }
            }
        }
    }
}

//主界面底部导航信息
data class MainInfo(
    val id: Int, val name: String, val icon: ImageVector
)

val mainInfoList = listOf(
    MainInfo(1, "首页", Icons.Filled.Home),
    MainInfo(2, "社区", Icons.Filled.DateRange),
    MainInfo(3, "AI小智", Icons.Filled.AccountBox),
    MainInfo(4, "消息", Icons.Filled.MailOutline),
    MainInfo(5, "我的", Icons.Filled.Person),
)

//自定义底部导航栏组件
@Preview(showBackground = true)
@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier, selectedId: Int = 1, onChangeId: (Int) -> Unit = {}
) {
    Row(modifier = modifier.shadow(elevation = 0.5.dp)) {
        mainInfoList.forEach { item ->

            //点击的动画效果
            val scale by animateFloatAsState(if (selectedId == item.id) 1.2f else 1f,
                animationSpec = if (selectedId == item.id) {
                    keyframes {
                        1f at 0
                        1.5f at 175
                        1.2f at 250
                        durationMillis = 250
                    }
                } else {
                    tween(durationMillis = 0) // 直接跳转，不执行动画
                })

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // 禁用水波纹效果
                    ) {
                        onChangeId(item.id)

                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (item.id == 3) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.scale(1f),
                        tint = Color(0XFF4157FF)
                    )
                    Spacer(Modifier.height(10.dp))
                } else {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.scale(scale),
                        tint = if (selectedId == item.id) Color(0XFF4157FF) else Color(0xFF090F47)
                    )
                }
            }
        }
    }
}
