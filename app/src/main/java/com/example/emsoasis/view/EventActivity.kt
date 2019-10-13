package com.example.emsoasis.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emsoasis.R
import com.example.emsoasis.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    lateinit var eventsViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        events.adapter = EventsAdapter()

    }
}
