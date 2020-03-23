package com.sambataro.ignacio.scoreboard.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sambataro.ignacio.scoreboard.domain.NewsInfo

@BindingAdapter("newsHeadline")
fun TextView.setNeweadlineNews(item: NewsInfo?) {
    item?.let {
        text =  item.headline
    }
}

@BindingAdapter("newsImage")
fun ImageView.setNewImage(item: NewsInfo?) {
    item?.let {
        Glide.with(this)
            .load(item.image)
            .apply(RequestOptions().override(385, 168))
            .into(this)
    }
}

@BindingAdapter("newsDescription")
fun TextView.setNewDescription(item: NewsInfo?) {
    item?.let {
        text = item.description
    }
}