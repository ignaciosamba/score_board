package com.sambataro.ignacio.scoreboard.ui.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sambataro.ignacio.scoreboard.databinding.CalendarItemBinding
import com.sambataro.ignacio.scoreboard.domain.DayCalendarInfo
import java.text.SimpleDateFormat
import java.util.*


class CalendarAdapter(val context: Context,
                      val clickListener: SelectDayListener) :
    ListAdapter<DayCalendarInfo, CalendarAdapter.ViewHolder>(CalendarDiffCallback()) {

    var lastPositionSelected = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            item.isSelected = false
            getItem(lastPositionSelected).isSelected = true
            holder.itemView.isSelected = true
            clickListener.onClick(item)
            lastPositionSelected =  position
            holder.bind(getItem(lastPositionSelected), clickListener, position)
        }

        val dateFormat = SimpleDateFormat("EEE, dd MMM. yyyy", Locale.getDefault())
        val dateFormatToSelectDay = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val calendarToSelectDay = Calendar.getInstance()
        val date = dateFormat.format(calendar.time).toString().replace("-", "")
        val dateToShow = dateFormatToSelectDay.format(calendarToSelectDay.time).toString()
        val dayCal = DayCalendarInfo(date.substringBefore(","),
            date.substringAfter(",").substringBefore("."),
            dateToShow, false)
        if (item.dateInfo == dayCal.dateInfo && !item.isSelected) {
            holder.itemView.isSelected = true
            lastPositionSelected =  position
        } else {
            holder.itemView.isSelected = false
        }
        if (position == lastPositionSelected) {
            holder.itemView.isSelected = true
        }

        holder.bind(item, clickListener, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: DayCalendarInfo,  clickListener: SelectDayListener, position: Int) {
            if (item.isSelected){
                itemView.isSelected = false
            }
            binding.calendarInfo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CalendarItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CalendarDiffCallback : DiffUtil.ItemCallback<DayCalendarInfo>() {

    override fun areItemsTheSame(oldItem: DayCalendarInfo, newItem: DayCalendarInfo): Boolean {
        return oldItem.dateInfo == newItem.dateInfo
    }

    override fun areContentsTheSame(oldItem: DayCalendarInfo, newItem: DayCalendarInfo): Boolean {
        return oldItem == newItem
    }
}

class SelectDayListener(val clickListener: (date: String) -> Unit) {
    fun onClick(dateInfo: DayCalendarInfo) {
        clickListener(dateInfo.dateFormatedToSelectOnClick)
    }
}