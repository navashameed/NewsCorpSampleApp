package com.newscorp.sampleapp.ui.sources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.newscorp.sampleapp.R
import com.newscorp.sampleapp.databinding.FragmentSourcesBinding
import com.newscorp.sampleapp.utils.ViewStateGeneric
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for displaying news sources
 */
@AndroidEntryPoint
class NewsSourcesFragment : Fragment() {

    private val newsSourcesViewModel: NewsSourcesViewModel by viewModels()

    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = newsSourcesViewModel
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_news_sources)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeToRefresh.setOnRefreshListener {
            newsSourcesViewModel.fetchSources()
        }

        newsSourcesViewModel.viewState.observe(viewLifecycleOwner) {
            if (it is ViewStateGeneric.Success || it is ViewStateGeneric.Error)
                binding.swipeToRefresh.isRefreshing = false
        }

        newsSourcesViewModel.fetchSources()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}