package com.newscorp.sampleapp.ui.headlines

import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.utils.displayDateInFormat

fun Article.getViewItem(viewModel: NewsHeadlinesViewModel) =
    NewsHeadlinesItemView(this, publishedAt?.displayDateInFormat()).apply { onItemClick = { viewModel.onNewsItemClick(this.article) } }