package com.newscorp.sampleapp.ui.databinding.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A generic ViewHolder that wraps a generated ViewDataBinding class.
 * <p>
 * Based on https://github.com/google/android-ui-toolkit-demos/tree/master/DataBinding/DataBoundRecyclerView.
 * <p>
 * Copyright (C) 2016 The Android Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class DataBoundViewHolder extends RecyclerView.ViewHolder {
    public final ViewDataBinding binding;

    public DataBoundViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Creates a new ViewHolder for the given layout file.
     * <p>
     * The provided layout must be using data binding.
     *
     * @param parent   The RecyclerView
     * @param layoutId The layout id that should be inflated. Must use data binding
     * @return A new ViewHolder that has a reference to the binding class
     */
    public static DataBoundViewHolder create(@NonNull ViewGroup parent,
                                             @LayoutRes int layoutId) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                layoutId, parent, false);
        return new DataBoundViewHolder(binding);
    }
}
