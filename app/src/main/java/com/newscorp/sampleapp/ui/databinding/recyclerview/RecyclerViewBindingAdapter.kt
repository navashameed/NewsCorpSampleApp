package com.newscorp.sampleapp.ui.databinding.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * [RecyclerView] binding adapters.
 *
 */

/**
 * Assigns [SingleDataBoundListAdapter] to [RecyclerView] from XML.
 *
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.headlinesItems}"
 *     app:itemLayout="@{@layout/headlines_item}"
 *     app:itemDiff="@{viewModel.headlinesItemDiff}" />
 * ```
 *
 */
@BindingAdapter("items", "itemLayout", "itemDiff")
fun <T> setAdapter(
    view: RecyclerView,
    items: LiveData<List<T>>,
    @LayoutRes itemLayout: Int,
    itemDiff: DiffUtil.ItemCallback<T>
) {
    if (view.adapter == null) {
        view.adapter = SingleDataBoundListAdapter(items, itemLayout, itemDiff)
    }
}

/**
 * Assigns [SingleDataBoundListAdapter] to [RecyclerView] from XML.
 * Defaulting the DIffUtils.ItemCallback<T> to NegativeItemCallBack<T>()
 * So don't need to define it in the ViewModel
 * We still require the items and itemLayout to be passed, hence didn't use
 * "requireAll = false" in the method above
 *
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.headlines}"
 *     app:itemLayout="@{@layout/headlines_item}"/>
 * ```
 *
 */

@BindingAdapter("items", "itemLayout")
fun <T : Any> setAdapter(
    view: RecyclerView,
    items: LiveData<List<T>>,
    @LayoutRes itemLayout: Int
) {
    if (view.adapter == null) {
        view.adapter = SingleDataBoundListAdapter(items, itemLayout, NegativeDiffCallback<T>())
    }
}

/**
 * Assigns [MultiDataBoundListAdapter] to [RecyclerView] from XML.
 *
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.headlines}"
 *     app:itemLayoutProvider="@{viewModel.headlinesLayout}"
 *     app:itemDiff="@{viewModel.headlinesDiff}" />
 * ```
 *
 */
@BindingAdapter("items", "itemLayoutProvider", "itemDiff")
fun <T> setAdapter(
    view: RecyclerView,
    items: LiveData<List<T>>,
    itemLayoutProvider: (T) -> Int,
    itemDiff: DiffUtil.ItemCallback<T>
) {
    if (view.adapter == null) {
        view.adapter = MultiDataBoundListAdapter(items, itemLayoutProvider, itemDiff)
    }
}

/**
 * Assigns [MultiDataBoundListAdapter] to [RecyclerView] from XML.
 * Defaulting the DIffUtils.ItemCallback<T> to NegativeItemCallBack<T>()
 * So don't need to define it in the ViewModel
 * We still require the items and itemLayoutProvider to be passed, hence didn't use
 * "requireAll = false" in the method above
 *
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.headlines}"
 *     app:itemLayoutProvider="@{viewModel.headlinesLayout}" />
 * ```
 *
 */

@BindingAdapter("items", "itemLayoutProvider")
fun <T : Any> setAdapter(
    view: RecyclerView,
    items: LiveData<List<T>>,
    itemLayoutProvider: (T) -> Int
) {
    if (view.adapter == null) {
        view.adapter =
            MultiDataBoundListAdapter(items, itemLayoutProvider, NegativeDiffCallback())
    }
}
