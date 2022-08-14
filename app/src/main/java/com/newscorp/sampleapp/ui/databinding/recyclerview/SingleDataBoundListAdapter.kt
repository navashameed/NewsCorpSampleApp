package com.newscorp.sampleapp.ui.databinding.recyclerview

import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil

/**
 * A RecyclerView [ListAdapter] for lists with items sharing the same single layout.
 *
 * Usage:
 *
 * 1. Define a ViewModel containing a list of items
 * ```
 * class NewsHeadlinesViewModel : ViewModel() {
 *     class HeadlinesListViewBase(val id: Int, val balanceText: String)
 *
 *     val headlinesItems = MutableLiveData<List<HeadlinesListViewBase>>()
 * }
 * ```
 *
 * 2. Provide a [DiffUtil.ItemCallback] for comparing list items (for example,
 * by exposing it as the ViewModel property).
 * ```
 * val itemsDiff = object: DiffUtil.ItemCallback<HeadlinesListViewBase>() {
 *     override fun areItemsTheSame(oldItem: HeadlinesListViewBase, newItem: HeadlinesListViewBase) = oldItem.id == newItem.id
 *     override fun areContentsTheSame(oldItem: HeadlinesListViewBase, newItem: HeadlinesListViewBase) = oldItem.balanceText == newItem.balanceText
 * }
 * ```
 *
 * 3. Design the item layout. The layout should use the databinding <variable> called `item`.
 * ```
 * <data>
 *     <variable name="item" type="...HeadlinesListViewBase"
 *     ...
 *     <TextView
 *         android:text="@{item.id}"
 *         ...
 *     <TextView
 *         android:text="@{item.balanceText}"
 *         ...
 * </data>
 * ```
 *
 * 4. Associate an instance of [SingleDataBoundListAdapter] with [RecyclerView]. This can be done either in code:
 * ```
 * recyclerView.adapter = SingleDataBoundListAdapter(viewModel.headlinesItems, R.layout.headlines_item_layout, viewModel.itemsDiff)
 * ```
 *
 * or in XML:
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.headlinesItems}"
 *     app:itemLayout="@{@layout/headlines_item_layout}"
 *     app:itemDiff="@{viewModel.itemsDiff}" />
 * ```
 *
 */
class SingleDataBoundListAdapter<T>(
    items: LiveData<List<T>>,
    @LayoutRes private val itemLayout: Int,
    itemDiff: DiffUtil.ItemCallback<T>
) : DataBoundListAdapter<T>(items, itemDiff) {

    @LayoutRes
    override fun getItemLayoutId(position: Int): Int = itemLayout
}
