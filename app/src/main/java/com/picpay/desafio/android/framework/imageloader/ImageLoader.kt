package com.picpay.desafio.android.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.picpay.desafio.android.R

interface ImageLoader {
    fun load(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes error: Int = R.drawable.ic_round_account_circle,
    )
}
