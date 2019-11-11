package com.omninos.kotlinproject.data.network.responses

data class NewsResponse(
    var status: String? = null,
    var totalResults: Int? = null,
    var articles: List<Article>? = null
)