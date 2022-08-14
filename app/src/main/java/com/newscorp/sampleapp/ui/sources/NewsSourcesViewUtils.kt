package com.newscorp.sampleapp.ui.sources

import com.newscorp.sampleapp.repository.model.Source

fun Source.getViewItem(viewModel: NewsSourcesViewModel, isSelected: Boolean = false) =
    NewsSourceItemView(this.id, this, isSelected).apply {
        onItemClick = { isChecked -> viewModel.onSourceItemClick(this.source, isChecked) }
    }