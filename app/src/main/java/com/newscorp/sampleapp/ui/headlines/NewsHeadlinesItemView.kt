package com.newscorp.sampleapp.ui.headlines

import com.newscorp.sampleapp.repository.model.Article

data class NewsHeadlinesItemView(val article: Article, val date: String?) {

    lateinit var onItemClick: () -> Unit
}