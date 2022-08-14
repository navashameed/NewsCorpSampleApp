package com.newscorp.sampleapp.ui.databinding.recyclerview

import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil

/**
 * A RecyclerView [ListAdapter] for lists with items where different items can have different layouts.
 *
 * Usage:
 *
 * 1. Define a ViewModel containing a list of items
 * ```
 * class NewsHeadlinesViewModel : ViewModel() {
 *     class HeadLinesListViewBase(val id: Int, val headLinesText: String)
 *
 *     val headlinesItems = MutableLiveData<List<HeadLinesListViewBase>>()
 * }
 * ```
 *
 * 2. Provide a function that maps items to item layouts (for example,
 * by exposing it as the ViewModel property).
 * ```
 * val itemLayout: (HeadLinesListViewBase) -> Int = { headlines -> R.layout.xxx}
 * ```
 *
 * 3. Provide a [DiffUtil.ItemCallback] for comparing list items (for example,
 * by exposing it as the ViewModel property).
 * ```
 * val itemsDiff = object: DiffUtil.ItemCallback<HeadLinesListViewBase>() {
 *     override fun areItemsTheSame(oldItem: HeadLinesListViewBase, newItem: HeadLinesListViewBase) = oldItem.id == newItem.id
 *     override fun areContentsTheSame(oldItem: HeadLinesListViewBase, newItem: HeadLinesListViewBase) = oldItem.headlinesText == newItem.headLinesText
 * }
 * ```
 *
 * 4. Design the item layouts. The layouts should use the databinding <variable> called `item`.
 * ```
 * <data>
 *     <variable name="item" type="...HeadLinesListViewBase"
 *     ...
 *     <TextView
 *         android:text="@{item.id}"
 *         ...
 *     <TextView
 *         android:text="@{item.headlinesText}"
 *         ...
 * </data>
 * ```
 *
 * 5. Associate an instance of [MultiDataBoundListAdapter] with [RecyclerView]. This can be done either in code:
 * ```
 * recyclerView.adapter = MultiDataBoundListAdapter(viewModel.headlinesItems, viewModel.itemLayout, viewModel.itemsDiff)
 * ```
 *
 * or in XML:
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.headlinesItems}"
 *     app:itemLayoutProvider="@{viewModel.itemLayout}"
 *     app:itemDiff="@{viewModel.itemsDiff}" />
 * ```
 *
 */
open class MultiDataBoundListAdapter<T>(
    items: LiveData<List<T>>,
    private val itemLayoutProvider: (T) -> Int,
    itemDiff: DiffUtil.ItemCallback<T>
) : DataBoundListAdapter<T>(items, itemDiff) {

    @LayoutRes
    override fun getItemLayoutId(position: Int): Int = itemLayoutProvider(getItem(position))
}
