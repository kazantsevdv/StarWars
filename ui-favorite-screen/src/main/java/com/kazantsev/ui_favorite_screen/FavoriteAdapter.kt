package com.kazantsev.ui_favorite_screen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kazantsev.ui_favorite_screen.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    val onListItemClickListener: OnListItemClickListener,
    val onItemFavoriteClickListener: OnListItemClickListener,
) :
    ListAdapter<FavoriteItemUi, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffItemCallback) {
    private lateinit var bindingItem: ItemFavoriteBinding


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            getItem(position)?.let { holder.bindFavoriteState(it.favorite) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        bindingItem =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(bindingItem)
    }

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FavoriteItemUi?) {
            data?.let {
                with(binding) {
                    root.setOnClickListener { onListItemClickListener.onItemClick(data.url,data.name) }
                    title.text = it.name
                    favorite.setOnClickListener { onItemFavoriteClickListener.onItemClick(data.url,data.name)}
                    if (data.favorite) {
                        favorite.setColorFilter(Color.MAGENTA)
                    } else {
                        favorite.setColorFilter(Color.GRAY)
                    }
                }
            }
        }

        fun bindFavoriteState(isFavorite: Boolean) {
            if (isFavorite) {
                binding.favorite.setColorFilter(Color.MAGENTA)
            } else {
                binding.favorite.setColorFilter(Color.GRAY)
            }
        }
    }
}

private object FavoriteDiffItemCallback : DiffUtil.ItemCallback<FavoriteItemUi>() {
    override fun areItemsTheSame(oldItem: FavoriteItemUi, newItem: FavoriteItemUi): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FavoriteItemUi, newItem: FavoriteItemUi): Boolean {
        return oldItem.name == newItem.name
    }

    override fun getChangePayload(oldItem: FavoriteItemUi, newItem: FavoriteItemUi): Any? {
        return if (oldItem.favorite != newItem.favorite) true else null
    }
}

interface OnListItemClickListener {
    fun onItemClick(personUrl: String,name:String)
}