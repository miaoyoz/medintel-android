package com.miaoyongzheng.medintel_android.ui.screen.mytest

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


data class TestState(
    val name: String = "miaoyoz"
)

@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TestState())
    val state = _state.asStateFlow()
}