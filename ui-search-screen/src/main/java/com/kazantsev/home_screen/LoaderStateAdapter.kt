package com.kazantsev.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kazantsev.home_screen.databinding.ItemLoadStateBinding


class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.ItemViewHolder>() {

    private lateinit var bindingItem: ItemLoadStateBinding

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {

        bindingItem.loadStateRetry.isVisible = loadState is LoadState.Error
        bindingItem.progress.isVisible = loadState is LoadState.Loading
        bindingItem.loadStateRetry.setOnClickListener {
            retry.invoke()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        bindingItem =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bindingItem)

    }


    inner class ItemViewHolder(loadStateViewBinding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(loadStateViewBinding.root)
}