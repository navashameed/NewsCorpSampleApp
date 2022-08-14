package com.newscorp.sampleapp.ui.sources

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
import com.newscorp.sampleapp.repository.model.Source
import com.newscorp.sampleapp.utils.ViewStateGeneric
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(
    private val repository: NewsCorpRepository,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    val sourcesList: LiveData<List<NewsSourceItemView>>
        get() = _sourcesList
    private val _sourcesList = MutableLiveData<List<NewsSourceItemView>>()

    val viewState: LiveData<ViewStateGeneric>
        get() = _viewState
    private val _viewState = MutableLiveData<ViewStateGeneric>(ViewStateGeneric.Loading)

    val sourcesListLayout = R.layout.list_item_source

    val sourcesListItemDiff = object : DiffUtil.ItemCallback<NewsSourceItemView>() {

        override fun areItemsTheSame(
            oldItem: NewsSourceItemView,
            newItem: NewsSourceItemView
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NewsSourceItemView,
            newItem: NewsSourceItemView
        ): Boolean = oldItem == newItem
    }

    fun onSourceItemClick(source: Source, isSelected: Boolean) {
        viewModelScope.launch {
            if (isSelected) {
                repository.saveSource(source)
            } else {
                repository.deleteSource(source)
            }
        }
    }

    fun fetchSources() {
        viewModelScope.launch {
            try {
                val sourcesList = repository.getNewsSources()
                sourcesList?.let {
                    _sourcesList.value = populateSourceWithSavedItems(sourcesList)
                }
                _viewState.value = ViewStateGeneric.Success
            } catch (e: IOException) {
                if (e is NetworkHttpException) {
                    _viewState.value = ViewStateGeneric.Error(e.message.orEmpty())
                } else {
                    // todo this is same code as above, might need to handle error differently later
                    _viewState.value = ViewStateGeneric.Error(e.message.orEmpty())
                }
            } catch (e: Exception) { // handle worse case
                Log.d("Exception", e.message.orEmpty()) // todo use a logging framework to log
                _viewState.value = ViewStateGeneric.Error()
            }
        }
    }

    private suspend fun populateSourceWithSavedItems(sources: List<Source>): List<NewsSourceItemView> {
        val newsItemsList: List<NewsSourceItemView> =
            withContext(dispatcherIO) {
                val savedSources = repository.getSavedSources()
                // create unique list of source from network and db
                val combinedSourcesList =
                    savedSources?.toMutableList()?.apply { addAll(sources) }?.toSet()?.toList()
                combinedSourcesList?.map {
                    val isSelected = savedSources.contains(it)
                    it.getViewItem(this@NewsSourcesViewModel, isSelected)
                } ?: emptyList()
            }
        return newsItemsList
    }
}


