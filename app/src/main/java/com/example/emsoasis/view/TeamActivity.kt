package com.example.emsoasis.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.emsoasis.R
import com.example.emsoasis.model.room.EventsData
import com.example.emsoasis.viewmodel.TeamViewModel
import com.example.emsoasis.viewmodel.TeamViewModelFactory
import kotlinx.android.synthetic.main.activity_team.*

class TeamActivity : AppCompatActivity(), TeamAdapter.OnTeamClicked {

    lateinit var teamViewModel: TeamViewModel
    lateinit var event: EventsData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        event = EventsData(id = intent.getIntExtra("eventId", 0), name = intent.getStringExtra("eventName"), minPlayers = intent.getIntExtra("eventMin", 1), maxPlayers = intent.getIntExtra("eventMax", 1))

        teamViewModel = ViewModelProviders.of(this, TeamViewModelFactory(eventId = event.id))[TeamViewModel::class.java]

        teams.adapter = TeamAdapter(this)
        teamViewModel.teams.observe(this, Observer {
            (teams.adapter as TeamAdapter).teams = it
            (teams.adapter as TeamAdapter).notifyDataSetChanged()
        })

        addTeam.setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java).also {
                it.putExtra("type", "team")
                it.putExtra("eventId", event.id)
            })
        }

        teamViewModel.error.observe(this, Observer {
            AlertDialog.Builder(this).setTitle("Error").setMessage(it).setNegativeButton("OK") { dialog, which ->
                dialog.dismiss()
            }
        })
    }

    override fun openTeamDetails(teamId: Int) {
        startActivity(Intent(this, MemberActivity::class.java).also {
            it.putExtra("eventId", event.id.toString())
            it.putExtra("teamId", teamId.toString())
        })
    }
}
