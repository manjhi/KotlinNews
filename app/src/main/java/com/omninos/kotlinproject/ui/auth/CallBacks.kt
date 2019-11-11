package com.omninos.kotlinproject.ui.auth

import com.omninos.kotlinproject.data.network.responses.Article
import com.omninos.kotlinproject.data.network.responses.NewsResponse

interface CallBacks {
    fun onStarted()
    fun onSuccess(newsResponse: NewsResponse)
    fun onFailer(message: String)
}