package com.picpay.desafio.android.framework.imageloader

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PicassoImageLoader @Inject constructor() : ImageLoader {
    override fun load(
        imageView: ImageView,
        imageUrl: String,
        error: Int,
    ) {
        Picasso.get()
            .load(imageUrl)
            .error(error)
            .into(imageView)
    }
}
