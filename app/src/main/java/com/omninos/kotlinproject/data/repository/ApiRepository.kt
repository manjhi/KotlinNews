package com.omninos.kotlinproject.data.repository

import com.omninos.kotlinproject.data.network.MyApi
import com.omninos.kotlinproject.data.network.SafeApiRequest
import com.omninos.kotlinproject.data.network.responses.NewsResponse

class ApiRepository(
    private val myApi: MyApi
) : SafeApiRequest() {
    suspend fun getNews(): NewsResponse {
        return apiRequest { myApi.getData() }
    }
}