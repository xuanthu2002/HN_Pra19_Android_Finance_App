package com.example.fina.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.fina.R

fun ImageView.loadImageCircleWithUrl(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImageWithUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.setIconChange(value: Double) {
    val iconRes = if (value > 0) R.drawable.up else R.drawable.down
    setImageResource(iconRes)
}
