package com.miaoyongzheng.medintel_android.ui.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miaoyongzheng.medintel_android.network.wanapi.HomeArticlesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class HomeArticle(
    val title: String,
    val chapterName: String,
    val niceShareDate: String,
    val link: String,
    val tags: List<String>,
    val zan: Boolean
)

data class HomeState(
    val avatar: String? = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRESdc7plAfA1hqGf-WoAs3NQfCzvIQ9VQMdA&s",
    val name: String = "缪永正",
    val searchInfo: String = "感冒吃什么药能快速好？",
    val hasNotification: Boolean = true,
    val isLoading: Boolean = false,
    val homeArticles: List<HomeArticle> = mutableListOf(),
    val currentPage: Int = 0,
    val isInitialArticle: Boolean = false

)

sealed class HomeIntent {
    data class LoadHomeArticles(val isFirst: Boolean) : HomeIntent()
}

class HomeViewModel : ViewModel() {

    private val _intent = MutableSharedFlow<HomeIntent>()
    private val intent = _intent.asSharedFlow()

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is HomeIntent.LoadHomeArticles -> loadHomeArticles(intent.isFirst)
                }
            }
        }
    }

    //对外公开的处理意图的方法
    fun processIntent(intent: HomeIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    //加载主页文章
    private suspend fun loadHomeArticles(isFirst: Boolean) = withContext(Dispatchers.IO) {
        _state.value = _state.value.copy(isLoading = true)
        val service = retrofit.create(HomeArticlesService::class.java)
        val call = service.getHomeArticles(_state.value.currentPage)
        val response = call.execute()
        if (response.isSuccessful) {
            val homeArticles = response.body()?.data?.datas?.map {
                HomeArticle(
                    title = it.title,
                    chapterName = it.chapterName,
                    niceShareDate = it.niceShareDate,
                    link = it.link,
                    tags = it.tags.map { tag ->
                        tag.name
                    },
                    zan = it.zan > 0
                )
            } ?: emptyList()
            //如果是第一次加载，则将isInitialArticle设置为true
            val currentState = _state.value
            if (isFirst) {
                _state.value = _state.value.copy(
                    homeArticles = currentState.homeArticles + homeArticles,
                    isInitialArticle = true,
                    isLoading = false, currentPage = currentState.currentPage + 1
                )
            } else {
                _state.value = _state.value.copy(
                    homeArticles = currentState.homeArticles + homeArticles,
                    isLoading = false, currentPage = currentState.currentPage + 1
                )
            }

        }

    }


}