package com.newscorp.sampleapp.repository

import com.newscorp.sampleapp.db.NewsCorpDb
import com.newscorp.sampleapp.network.NetworkHub
import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.repository.model.Source
import com.newscorp.sampleapp.repository.model.toArticlesUIModel
import com.newscorp.sampleapp.repository.model.toSourcesUIModel
import javax.inject.Inject

/**
 * Repository to get news corp API and database related data
 */
class NewsCorpRepositoryImpl @Inject constructor(
    private val networkHub: NetworkHub,
    private val newsCorpDb: NewsCorpDb
) :
    NewsCorpRepository {

    override suspend fun getNewsHeadLines(sources: String): List<Article>? {
        return networkHub.getHeadlines(sources)?.articles?.map {
            it.toArticlesUIModel()
        }
    }

    override suspend fun getNewsSources(): List<Source>? {
        return networkHub.getSources()?.sources?.map {
            it.toSourcesUIModel()
        }
    }

    override suspend fun getSavedNewsArticles(): List<Article>? =
        newsCorpDb.articleDao().getArticles()

    override suspend fun isArticleSaved(id: String): Boolean =
        newsCorpDb.articleDao().isArticleSaved(id)

    override suspend fun saveNewsArticle(article: Article) =
        newsCorpDb.articleDao().insertArticle(article) != -1L

    override suspend fun deleteNewsArticle(article: Article) =
        newsCorpDb.articleDao().deleteArticle(article) != -1


    override suspend fun getSavedSources(): List<Source>? = newsCorpDb.sourcesDao().getSources()

    override suspend fun saveSource(source: Source): Boolean =
        newsCorpDb.sourcesDao().insertSource(source) != -1L

    override suspend fun deleteSource(source: Source): Boolean =
        newsCorpDb.sourcesDao().deleteSource(source) != -1


}