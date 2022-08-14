package com.newscorp.sampleapp.db

import androidx.room.*
import com.newscorp.sampleapp.repository.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(entity: Article): Long

    @Delete
    suspend fun deleteArticle(entity: Article): Int?

    @Query("SELECT EXISTS(SELECT * FROM article WHERE id = :id)")
    suspend fun isArticleSaved(id: String): Boolean

    @Query("SELECT * FROM article")
    suspend fun getArticles(): List<Article>?

}
