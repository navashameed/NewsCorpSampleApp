package com.newscorp.sampleapp.network

import com.newscorp.sampleapp.network.model.ArticleResponse
import com.newscorp.sampleapp.network.model.SourceResponse

interface NetworkHub {
    suspend fun getHeadlines(sources: String): ArticleResponse?

    suspend fun getSources(): SourceResponse?
}