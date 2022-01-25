package com.kazantsev.data.image

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoaderImpl : ImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            //.placeholder(R.drawable.stub)
            .circleCrop()
            .load(url)
            .into(container)
    }
}