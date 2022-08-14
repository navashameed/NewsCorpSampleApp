package com.newscorp.sampleapp.repository.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

fun com.newscorp.sampleapp.network.model.Article.toArticlesUIModel(): Article =
    Article(
        category,
        author,
        title,
        description,
        url,
        publishedAt,
        urlToImage,
        content,
        source?.id,
        source?.name
    )

fun com.newscorp.sampleapp.network.model.Source.toSourcesUIModel(): Source =
    Source(id.orEmpty(), name, description, url, category, language, country)

@Entity
data class Source(
    @PrimaryKey
    val id: String = "",
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?,
    val isSaved: Boolean = false // only for db
)

@Parcelize
@Entity
data class Article(
    val category: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val publishedAt: Date?,
    val urlToImage: String?,
    val content: String?,
    val articleSourceId: String?,
    val articleSourceName: String?
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey
    var id: String = "$title$url"
}

data class ArticleSource(val id: String?, val name: String?)