package com.example.emsoasis.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emsoasis.R
import com.example.emsoasis.model.retrofit.MemberPojo
import kotlinx.android.synthetic.main.adapter_member.view.*

class MemberAdapter(): RecyclerView.Adapter<MemberAdapter.ViewHolder>(){

    var members: List<MemberPojo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_member, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = members[position].name
        holder.college.text = members[position].college
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.name
        val college: TextView = view.collage
    }
}