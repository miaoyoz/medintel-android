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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miaoyongzheng.medintel_android.BeginRoute
import com.miaoyongzheng.medintel_android.ui.component.Toast
import com.miaoyongzheng.medintel_android.ui.screen.main.community.CommunityScreen
import com.miaoyongzheng.medintel_android.ui.screen.main.home.HomeScreen
import com.miaoyongzheng.medintel_android.ui.screen.main.message.MessageScreen
import com.miaoyongzheng.medintel_android.ui.screen.main.profile.ProfileScreen
import kotlinx.coroutines.delay

object MainRoute {
    const val HOME = "HomeScreen"
    const val COMMUNITY = "CommunityScreen"
    const val MESSAGE = "MessageScreen"
    const val PROFILE = "ProfileScreen"
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
    //当前导航的路由
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //返回键监听
    BackHandler(enabled = true) {
        //如果当前不是Home，则会返回到Home
        if (currentRoute != MainRoute.HOME) {
            navController.navigate(MainRoute.HOME) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true // 保存当前状态
                }
                launchSingleTop = true // 确保只有一个实例
                restoreState = true
            }
        } else {
            //误触判断
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressTime > 2000) {
                showToast = true
                showAnimation = true
                backPressTime = currentTime
            } else {
                activity?.finish()
            }
        }
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        bottomBar = {
            MainBottomBar(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                currentRoute = currentRoute,
                onNavigate = {
                    when (it) {
                        BeginRoute.CHATBOT -> onNavigateToBot()
                        else -> navController.navigate(it) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true // 保存当前状态
                            }
                            launchSingleTop = true // 确保只有一个实例
                            restoreState = true
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
                    startDestination = MainRoute.HOME,
                ) {
                    composable(MainRoute.HOME) {
                        HomeScreen()
                    }
                    composable(MainRoute.COMMUNITY) {
                        CommunityScreen()
                    }

                    composable(MainRoute.MESSAGE) {
                        MessageScreen()
                    }
                    composable(MainRoute.PROFILE) {
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
    val name: String, val icon: ImageVector, val route: String
)

val mainInfoList = listOf(
    MainInfo("首页", Icons.Filled.Home, MainRoute.HOME),
    MainInfo("社区", Icons.Filled.DateRange, MainRoute.COMMUNITY),
    MainInfo("AI小智", Icons.Filled.AccountBox, BeginRoute.CHATBOT),
    MainInfo("消息", Icons.Filled.MailOutline, MainRoute.MESSAGE),
    MainInfo("我的", Icons.Filled.Person, MainRoute.PROFILE),
)

//底部导航栏组件
@Preview(showBackground = true)
@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier, onNavigate: (String) -> Unit = {},
    currentRoute: String? = MainRoute.HOME
) {
    Row(modifier = modifier.shadow(elevation = 0.5.dp)) {
        mainInfoList.forEach { item ->

            val selected = currentRoute == item.route

            //点击的动画效果
            val scale by animateFloatAsState(if (selected) 1.2f else 1f,
                animationSpec = if (selected) {
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
                        Log.d("miaoyoz1", "MainBottomBar: ${item.route} + $currentRoute")
                        onNavigate(item.route)
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (item.route == BeginRoute.CHATBOT) {
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
                        tint = if (selected) Color(0XFF4157FF) else Color(0xFF090F47)
                    )
                }
            }
        }
    }
}
