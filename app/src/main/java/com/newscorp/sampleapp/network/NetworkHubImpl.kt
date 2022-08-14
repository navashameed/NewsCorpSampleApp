package com.newscorp.sampleapp.network

import com.newscorp.sampleapp.network.model.ArticleResponse
import com.newscorp.sampleapp.network.model.SourceResponse
import com.newscorp.sampleapp.network.retrofit.NewsApiInterface
import javax.inject.Inject

class NetworkHubImpl @Inject constructor(private val newsApiInterface: NewsApiInterface) :
    NetworkHub {
    override suspend fun getHeadlines(sources: String): ArticleResponse? {
        if (sources.isNotEmpty()) {
            // cannot mix country and source
            return newsApiInterface.getHeadlines(sources = sources, countryDefault = null)
                .successBodyOrException()
        }
        return newsApiInterface.getHeadlines().successBodyOrException()
    }

    override suspend fun getSources(): SourceResponse? {
        return newsApiInterface.getSources().successBodyOrException()
    }
}

