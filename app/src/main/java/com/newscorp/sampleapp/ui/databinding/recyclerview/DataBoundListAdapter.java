package com.newscorp.sampleapp.ui.databinding.recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * An implementation of {@link BaseDataBoundAdapter} that uses LiveData List as the item storage.
 * <p>
 * A reference implementation for an adapter that wants to use data binding "the right way". It
 * works with {@link DataBoundViewHolder}.
 * <p>
 * This class uses layout id as the item type.
 * <p>
 * It can be used for both single type lists and multiple type lists.
 */

public abstract class DataBoundListAdapter<T>
        extends BaseDataBoundAdapter<T> {

    private final AsyncListDiffer<T> mDiffer;

    protected DataBoundListAdapter(@NonNull LiveData<List<T>> items, @NonNull DiffUtil.ItemCallback<T> itemDiff) {
        mDiffer = new AsyncListDiffer<>(new AdapterListUpdateCallback(this),
                new AsyncDifferConfig.Builder<>(itemDiff).build());

        items.observe(this, this::submitList);
    }

    /**
     * Submits a new list to be diffed, and displayed.
     * <p>
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the main thread.
     *
     * @param list The new list to be displayed.
     */
    private void submitList(@Nullable List<T> list) {
        mDiffer.submitList(list);
    }

    protected final T getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    @Override
    public final int getItemCount() {
        return mDiffer.getCurrentList().size();
    }
}
