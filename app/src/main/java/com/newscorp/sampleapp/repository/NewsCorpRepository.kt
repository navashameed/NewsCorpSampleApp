package com.newscorp.sampleapp.repository

import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.repository.model.Source

interface NewsCorpRepository {

    suspend fun getNewsHeadLines(sources: String): List<Article>?

    suspend fun getNewsSources(): List<Source>?

    suspend fun getSavedNewsArticles(): List<Article>?

    suspend fun isArticleSaved(id: String): Boolean

    suspend fun saveNewsArticle(article: Article): Boolean

    suspend fun deleteNewsArticle(article: Article): Boolean

    suspend fun getSavedSources(): List<Source>?

    suspend fun saveSource(source: Source): Boolean

    suspend fun deleteSource(source: Source): Boolean
}
