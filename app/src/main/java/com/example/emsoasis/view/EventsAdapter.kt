package com.example.emsoasis.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emsoasis.R
import com.example.emsoasis.model.room.EventsData
import kotlinx.android.synthetic.main.adapter_events.view.*

class EventsAdapter(private val listener: OnEventClick): RecyclerView.Adapter<EventsAdapter.EventsViewHolder>(){

    var events: List<EventsData> = emptyList()

    interface OnEventClick{
        fun changeActivity(event: EventsData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_events, parent, false)
        return EventsViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        holder.eventName.text = events[position].name
        holder.eventName.setOnClickListener {
            listener.changeActivity(events[position])
        }
    }

    inner class EventsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val eventName: TextView = view.eventName
    }
}