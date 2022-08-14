package com.newscorp.sampleapp.ui.databinding.recyclerview

import androidx.recyclerview.widget.DiffUtil

/**
 * The DiffUtil callback that treats all items as different.
 */
class NegativeDiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false
}
