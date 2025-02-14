package com.picpay.desafio.android.presentation.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailViewArg(
    val img: String,
    val name: String,
    val id: Int,
    val username: String
) : Parcelable
