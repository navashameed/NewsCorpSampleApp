package com.newscorp.sampleapp.ui.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.newscorp.sampleapp.R
import com.newscorp.sampleapp.databinding.FragmentHeadlinesBinding
import com.newscorp.sampleapp.ui.saved.NewsSavedFragmentDirections
import com.newscorp.sampleapp.utils.ViewStateGeneric
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for displaying news headlines list
 */
@AndroidEntryPoint
open class NewsHeadLinesFragment : Fragment() {

    protected val newsHeadlinesViewModel: NewsHeadlinesViewModel by viewModels()

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = newsHeadlinesViewModel
        }

        setUpViews()
        setUpViewModelObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsHeadlinesViewModel.fetchNewsArticles()
        binding.toolbar.title = if (newsHeadlinesViewModel.savedArticlesMode) {
            getString(R.string.title_news_saved)
        } else {
            getString(R.string.title_headlines)
        }
    }

    private fun setUpViews() {

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_news_list)

        binding.swipeToRefresh.setOnRefreshListener {
            newsHeadlinesViewModel.fetchNewsArticles()
        }
    }

    private fun setUpViewModelObservers() {

        newsHeadlinesViewModel.viewState.observe(viewLifecycleOwner) {
            if (it is ViewStateGeneric.Success || it is ViewStateGeneric.Error)
                binding.swipeToRefresh.isRefreshing = false
        }

        newsHeadlinesViewModel.navigateToNewsDetails.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { article ->
                val action = if (newsHeadlinesViewModel.savedArticlesMode) {
                    NewsSavedFragmentDirections.actionSavedToDetails(article)
                } else {
                    NewsHeadLinesFragmentDirections.actionHeadlinesToDetails(article)
                }

                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}