package com.newscorp.sampleapp.network.model

import java.util.*

data class SourceResponse(val status: String, val sources: List<Source>)

data class ArticleResponse(val status: String, val totalResults: Int, val articles: List<Article>)

data class Source(
    val id: String?,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?
)

data class Article(
    val category: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val publishedAt: Date?,
    val urlToImage: String?,
    val source: ArticleSource?,
    val content: String?
)

data class ArticleSource(val id: String?, val name: String?)