package com.kazantsev.detail_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kazantsev.detail_screen.databinding.ItemVideoBinding
import com.kazantsev.domain.entity.FilmInfo

class FilmAdapter :
    ListAdapter<FilmInfo, FilmAdapter.FilmViewHolder>(FavoriteDiffItemCallback) {
    private lateinit var bindingItem: ItemVideoBinding

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        bindingItem =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(bindingItem)
    }

    inner class FilmViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FilmInfo?) {
            data?.let {
                with(binding) {
                    title.text = it.title
                }
            }
        }
    }
}

private object FavoriteDiffItemCallback : DiffUtil.ItemCallback<FilmInfo>() {
    override fun areItemsTheSame(oldItem: FilmInfo, newItem: FilmInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FilmInfo, newItem: FilmInfo): Boolean {
        return oldItem.title == newItem.title
    }
}
