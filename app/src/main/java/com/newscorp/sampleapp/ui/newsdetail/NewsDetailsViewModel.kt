package com.newscorp.sampleapp.ui.newsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newscorp.sampleapp.repository.NewsCorpRepository
import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.ui.headlines.NewsHeadlinesItemView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val repository: NewsCorpRepository
) : ViewModel() {

    val isArticleSaved: LiveData<Boolean>
        get() = _isArticleSaved
    private val _isArticleSaved = MutableLiveData<Boolean>()

    fun checkArticleStatus(article: Article) {
        viewModelScope.launch {
            val isSaved = repository.isArticleSaved(article.id)
            _isArticleSaved.value = isSaved
        }
    }

    fun onSaveOrDeleteClicked(article: Article) {
        if (isArticleSaved.value == true) {
            deleteArticle(article)
        } else {
            saveArticle(article)
        }
    }

    private fun saveArticle(article: Article) {
        viewModelScope.launch {
            val isSuccess = repository.saveNewsArticle(article)
            if (isSuccess) {
                _isArticleSaved.value = true
            }

        }
    }

    private fun deleteArticle(article: Article) {
        viewModelScope.launch {
            val isSuccess = repository.deleteNewsArticle(article)
            if (isSuccess) {
                _isArticleSaved.value = false
            }
        }
    }
}


