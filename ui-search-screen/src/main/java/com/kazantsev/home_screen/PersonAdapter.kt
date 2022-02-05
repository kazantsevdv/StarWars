package com.kazantsev.home_screen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kazantsev.home_screen.databinding.ItemPersonBinding

class PersonAdapter(
    val onListItemClickListener: OnListItemClickListener,
    val onItemFavoriteClickListener: OnListItemClickListener,
) :
    PagingDataAdapter<PersonInfoUi, PersonAdapter.PersonViewHolder>(PersonDiffItemCallback) {
    private lateinit var bindingItem: ItemPersonBinding

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: PersonViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            getItem(position)?.let { holder.bindFavoriteState(it.favorite) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        bindingItem = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(bindingItem)
    }

    inner class PersonViewHolder(val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PersonInfoUi?) {
            data?.let {
                with(binding) {
                    root.setOnClickListener {
                        onListItemClickListener.onItemClick(data.url,
                            data.name)
                    }
                    title.text = it.name
                    favorite.setOnClickListener {
                        onItemFavoriteClickListener.onItemClick(data.url,
                            data.name)
                    }
                    favorite.setColorFilter(if (it.favorite) Color.MAGENTA else Color.GRAY)
                }
            }
        }

        fun bindFavoriteState(isFavorite: Boolean) =
            binding.favorite.setColorFilter(if (isFavorite) Color.MAGENTA else Color.GRAY)
    }
}

private object PersonDiffItemCallback : DiffUtil.ItemCallback<PersonInfoUi>() {
    override fun areItemsTheSame(oldItem: PersonInfoUi, newItem: PersonInfoUi): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PersonInfoUi, newItem: PersonInfoUi): Boolean {
        return oldItem.url == newItem.url
    }

    override fun getChangePayload(oldItem: PersonInfoUi, newItem: PersonInfoUi): Any? {
        return if (oldItem.favorite != newItem.favorite) true else null
    }
}

interface OnListItemClickListener {
    fun onItemClick(personUrl: String, name: String)
}