package com.newscorp.sampleapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newscorp.sampleapp.ui.headlines.NewsHeadLinesFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for displaying saved news, reusing NewsHeadLinesFragment with flag set for savedMode
 */
@AndroidEntryPoint
class NewsSavedFragment : NewsHeadLinesFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        newsHeadlinesViewModel.savedArticlesMode = true
        return view
    }
}