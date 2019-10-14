package com.example.emsoasis.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.emsoasis.R
import com.example.emsoasis.viewmodel.MemberViewModel
import com.example.emsoasis.viewmodel.MemberViewModelFactory

class MemberActivity : AppCompatActivity() {

    lateinit var viewModel: MemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
        val recycler = findViewById<RecyclerView>(R.id.members)
        recycler.adapter = MemberAdapter()

        val eventId = intent!!.getStringExtra("eventId")
        val teamId = intent!!.getStringExtra("teamId")
        Log.d("Member Activity", "Event Id = $eventId, TeamId = $teamId")

        viewModel = ViewModelProviders.of(this, MemberViewModelFactory(eventId = eventId, teamId = teamId))[MemberViewModel::class.java]

        val button = findViewById<Button>(R.id.addMember)
        button.setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java).also {
                it.putExtra("type", "player")
                it.putExtra("eventId", eventId)
                it.putExtra("teamId", teamId)
            })
        }

        viewModel.members.observe(this, Observer {
            (recycler.adapter as MemberAdapter).members = it
            (recycler.adapter as MemberAdapter).notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.refreshEvents()
    }
}
