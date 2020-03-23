package com.sambataro.ignacio.scoreboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sambataro.ignacio.scoreboard.domain.NewsInfo

@Entity(tableName = "news_table")
class NewsEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val image: String,
    val description: String,
    val headline: String,
    var storyLink: String)

fun List<NewsEntity>.asNewsDomainModel(): List<NewsInfo> {
    return map {
        NewsInfo(
            image = it.image,
            description = it.description,
            headline = it.headline,
            storyLink = it.storyLink
        )
    }
}