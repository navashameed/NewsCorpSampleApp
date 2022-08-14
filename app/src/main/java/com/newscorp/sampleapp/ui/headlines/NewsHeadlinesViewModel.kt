package com.newscorp.sampleapp.ui.headlines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.newscorp.sampleapp.R
import com.newscorp.sampleapp.di.DispatcherIO
import com.newscorp.sampleapp.network.NetworkHttpException
import com.newscorp.sampleapp.repository.NewsCorpRepository
import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.utils.ViewStateGeneric
import com.newscorp.sampleapp.utils.lifecycle.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsHeadlinesViewModel @Inject constructor(
    private val repository: NewsCorpRepository,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    val headlinesList: LiveData<List<NewsHeadlinesItemView>>
        get() = _headlinesList
    private val _headlinesList = MutableLiveData<List<NewsHeadlinesItemView>>()

    val viewState: LiveData<ViewStateGeneric>
        get() = _viewState
    private val _viewState = MutableLiveData<ViewStateGeneric>(ViewStateGeneric.Loading)

    val navigateToNewsDetails: LiveData<Event<Article>>
        get() = _navigateToNewsDetails
    private val _navigateToNewsDetails = MutableLiveData<Event<Article>>()

    val headlinesListLayout = R.layout.list_item_headlines

    var savedArticlesMode = false

    val headlinesListItemDiff = object : DiffUtil.ItemCallback<NewsHeadlinesItemView>() {

        override fun areItemsTheSame(
            oldItem: NewsHeadlinesItemView,
            newItem: NewsHeadlinesItemView
        ): Boolean = oldItem == newItem // no unique id, so do equals

        override fun areContentsTheSame(
            oldItem: NewsHeadlinesItemView,
            newItem: NewsHeadlinesItemView
        ): Boolean = oldItem == newItem
    }

    fun onNewsItemClick(article: Article) {
        _navigateToNewsDetails.value = Event(article)
    }

    fun fetchNewsArticles() {
        viewModelScope.launch {
            try {
                val articlesList = if (savedArticlesMode) {
                    repository.getSavedNewsArticles()
                } else {
                    val sources =
                        repository.getSavedSources()?.map { it.id }?.joinToString(separator = ",")
                    repository.getNewsHeadLines(sources.orEmpty())
                }

                _headlinesList.value = populateHeadlinesListView(articlesList)
                _viewState.value = ViewStateGeneric.Success
            } catch (e: IOException) {
                if (e is NetworkHttpException) {
                    _viewState.value = ViewStateGeneric.Error(e.message.orEmpty())
                } else {
                    // todo this is currently the same code as above, but we might need to handle it differently
                    _viewState.value = ViewStateGeneric.Error(e.message.orEmpty())
                }
            } catch (e: Exception) { // handle worse case
                Log.d("Exception", e.message.orEmpty()) // todo use a logging framework to log
                _viewState.value = ViewStateGeneric.Error()
            }
        }
    }

    private suspend fun populateHeadlinesListView(articlesList: List<Article>?): List<NewsHeadlinesItemView> {
        val _articlesList: List<NewsHeadlinesItemView> = withContext(dispatcherIO) {
            articlesList?.map { it.getViewItem(this@NewsHeadlinesViewModel) }.orEmpty()
        }
        return _articlesList
    }
}
