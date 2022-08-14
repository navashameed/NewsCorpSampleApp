package com.newscorp.sampleapp.utils

/**
 * Generic view state to pass data from viewModel to view binding.
 */
sealed class ViewStateGeneric {
    object Loading : ViewStateGeneric()
    object Success : ViewStateGeneric()
    data class Error(val message: String = "") : ViewStateGeneric()
}
