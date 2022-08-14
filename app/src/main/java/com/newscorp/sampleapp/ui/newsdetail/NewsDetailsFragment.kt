package com.newscorp.sampleapp.ui.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.newscorp.sampleapp.R
import com.newscorp.sampleapp.databinding.FragmentNewsDetailsBinding
import com.newscorp.sampleapp.utils.setUpNavigation
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for displaying news details in web view
 */
@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    private val newsDetailsViewModel: NewsDetailsViewModel by viewModels()

    private val args: NewsDetailsFragmentArgs by navArgs()

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        binding.toolbar.setUpNavigation(findNavController())
        binding.toolbar.title = ""

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.inflateMenu(R.menu.menu_options_save)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    newsDetailsViewModel.onSaveOrDeleteClicked(args.article)
                    true
                }
                else -> false
            }
        }

        setViewModelObservers()
        setUpWebView()
        setUpBackButton()

        newsDetailsViewModel.checkArticleStatus(args.article)
    }

    private fun setViewModelObservers() {
        newsDetailsViewModel.isArticleSaved.observe(viewLifecycleOwner) {
            setArticleSelected(it)
        }
    }

    private fun setArticleSelected(selected: Boolean) {
        val drawable = if (selected) R.drawable.ic_save_selected else R.drawable.ic_save
        binding.toolbar.menu.getItem(0).setIcon(drawable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpWebView() {
        args.article.url?.let { binding.webview.loadUrl(it) }
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }
        }
    }

    private fun setUpBackButton() {
        // check whether webview has any links to go back to, else call do the activity backpress
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val webView = binding.webview
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        if (isEnabled) {
                            isEnabled = false
                            requireActivity().onBackPressed()
                        }
                    }
                }
            }
            )
    }
}