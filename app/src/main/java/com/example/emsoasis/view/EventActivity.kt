package com.example.emsoasis.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.emsoasis.R
import com.example.emsoasis.model.room.EventsData
import com.example.emsoasis.viewmodel.EventViewModel
import com.example.emsoasis.viewmodel.EventViewModelFactory
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity(), EventsAdapter.OnEventClick {

    lateinit var eventsViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        eventsViewModel = ViewModelProviders.of(this, EventViewModelFactory())[EventViewModel::class.java]

        events.adapter = EventsAdapter(this)
        eventsViewModel.events.observe(this, Observer {
            (events.adapter as EventsAdapter).events = it
            (events.adapter as EventsAdapter).notifyDataSetChanged()
        })

        eventsViewModel.error.observe(this, Observer {
            AlertDialog.Builder(this).setTitle("Error").setMessage(it).setNegativeButton("OK") { dialog, which ->
                dialog.dismiss()
            }
        })

    }

    override fun changeActivity(event: EventsData) {

        startActivity(Intent(this, TeamActivity::class.java).also {
            it.putExtra("eventId", event.id)
            it.putExtra("eventName", event.name)
            it.putExtra("eventMax", event.maxPlayers)
            it.putExtra("eventMin", event.minPlayers)
        })
    }
}
