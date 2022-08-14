package com.newscorp.sampleapp.network.retrofit

import com.newscorp.sampleapp.BuildConfig
import com.newscorp.sampleapp.network.model.ArticleResponse
import com.newscorp.sampleapp.network.model.SourceResponse
import com.newscorp.sampleapp.network.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    /**
     * API Call for fetching news headlines
     */
    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("apiKey") apiKey: String = BuildConfig.NewsApiKey,
        @Query("country") countryDefault: String? = NetworkConstants.API_COUNTRY_DEFAULT,
        @Query("sources") sources: String? = null
    ): Response<ArticleResponse>

    /**
     * API Call for fetching news headlines
     */
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = BuildConfig.NewsApiKey,
        @Query("country") countryDefault: String? = NetworkConstants.API_COUNTRY_DEFAULT
    ): Response<SourceResponse>
}