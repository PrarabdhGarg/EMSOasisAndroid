package com.example.emsoasis.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.emsoasis.R
import com.example.emsoasis.viewmodel.EventViewModel
import com.example.emsoasis.viewmodel.EventViewModelFactory
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    lateinit var eventsViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        eventsViewModel = ViewModelProviders.of(this, EventViewModelFactory())[EventViewModel::class.java]

        events.adapter = EventsAdapter()
        eventsViewModel.events.observe(this, Observer {
            (events.adapter as EventsAdapter).events = it
            (events.adapter as EventsAdapter).notifyDataSetChanged()
        })

    }
}
