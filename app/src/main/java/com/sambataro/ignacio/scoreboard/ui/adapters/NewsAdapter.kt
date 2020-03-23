package com.sambataro.ignacio.scoreboard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sambataro.ignacio.scoreboard.databinding.NewsItemBinding
import com.sambataro.ignacio.scoreboard.domain.NewsInfo

class NewsAdapter : ListAdapter<NewsInfo, NewsAdapter.ViewHolder>(NewsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: NewsInfo) {
            binding.newsInfo = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class NewsDiffCallback : DiffUtil.ItemCallback<NewsInfo>() {

    override fun areItemsTheSame(oldItem: NewsInfo, newItem: NewsInfo): Boolean {
        return oldItem.headline == newItem.headline
    }


    override fun areContentsTheSame(oldItem: NewsInfo, newItem: NewsInfo): Boolean {
        return oldItem == newItem
    }
}

