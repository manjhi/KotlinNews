package com.omninos.kotlinproject.ui.auth

import androidx.lifecycle.ViewModel
import com.omninos.kotlinproject.data.repository.ApiRepository
import com.omninos.kotlinproject.util.ApiException
import com.omninos.kotlinproject.util.Coroutines

class MyViewModel : ViewModel() {

    var listener: CallBacks? = null
    suspend fun getNewsData() {
        listener?.onStarted()
        Coroutines.main {
            try {
                val responseBody = ApiRepository().getNews()
                responseBody.articles?.let {
                    listener?.onSuccess(responseBody)
                    return@main
                }
            } catch (e: ApiException) {
                listener?.onFailer("Error" + e.toString())
            }
        }
    }
}