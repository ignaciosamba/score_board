package com.sambataro.ignacio.scoreboard.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sambataro.ignacio.scoreboard.domain.GameScoreInfo

@BindingAdapter("homeTeamName")
fun TextView.setHomeTeamName(item: GameScoreInfo?) {
    item?.let {
        text =  item.home_name
    }
}

@BindingAdapter("homeTeamLogo")
fun ImageView.setHomeTeamLogo(item: GameScoreInfo?) {
    item?.let {
        Glide.with(this)
            .load(item.home_logo)
            .apply(RequestOptions().override(100, 100))
            .into(this)
    }
}

@BindingAdapter("homeTeamScore")
fun TextView.setHomeTeamNameScore(item: GameScoreInfo?) {
    item?.let {
        text = item.home_score
    }
}

@BindingAdapter("awayTeamName")
fun TextView.setAwayTeamName(item: GameScoreInfo?) {
    item?.let {
        text =  item.away_name
    }
}

@BindingAdapter("awayTeamLogo")
fun ImageView.setAwayTeamLogo(item: GameScoreInfo?) {
    item?.let {
        Glide.with(this)
            .load(item.away_logo)
            .apply(RequestOptions().override(100, 100))
            .into(this)
    }
}

@BindingAdapter("awayTeamScore")
fun TextView.setAwayTeamScore(item: GameScoreInfo?) {
    item?.let {
        text = item.away_score
    }
}

@BindingAdapter("dateGame")
fun TextView.setDateGame(item: GameScoreInfo?) {
    item?.let {
        text = item.game_date
    }
}

@BindingAdapter("statusGame")
fun TextView.setStatusGame(item: GameScoreInfo?) {
    item?.let {
        text = item.game_status
    }
}