package com.miaoyongzheng.medintel_android.ui.screen.main.home

import android.app.Notification
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class HomeState(
    val avatar: String? = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRESdc7plAfA1hqGf-WoAs3NQfCzvIQ9VQMdA&s",
    val name: String = "缪永正",
    val searchInfo:String = "感冒吃什么药能快速好？",
    val hasNotification: Boolean = true,
)

class HomeViewModel: ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()


    fun test(){

    }
}