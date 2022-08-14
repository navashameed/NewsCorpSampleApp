package com.newscorp.sampleapp.ui.sources

import com.newscorp.sampleapp.repository.model.Source

data class NewsSourceItemView(val id: String, val source: Source, val isSelected: Boolean) {

    lateinit var onItemClick: (isChecked: Boolean) -> Unit
}