package com.example.emsoasis.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emsoasis.R
import com.example.emsoasis.model.retrofit.TeamPojo
import kotlinx.android.synthetic.main.adapter_teams.view.*

class TeamAdapter(private val listener: OnTeamClicked): RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(){

    var teams: List<TeamPojo> = emptyList()

    interface OnTeamClicked{
        fun openTeamDetails(teamId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_teams, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.teamName.text = teams[position].name
        holder.teamName.setOnClickListener {
            listener.openTeamDetails(teams[position].id)
        }
    }

    inner class TeamViewHolder(view: View): RecyclerView.ViewHolder(view){
        val teamName: TextView = view.teamName
    }
}