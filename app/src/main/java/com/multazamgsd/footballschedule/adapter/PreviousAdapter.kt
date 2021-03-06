package com.multazamgsd.footballschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.multazamgsd.footballschedule.R
import com.multazamgsd.footballschedule.R.id.textViewMatch
import com.multazamgsd.footballschedule.R.id.textViewMatchDate
import com.multazamgsd.footballschedule.model.Events

class PreviousAdapter(private val context: Context?, private val prev: List<Events>, private val listener: (Events) -> Unit)
    : RecyclerView.Adapter<PreviousAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.prev_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(prev[position], listener)
    }

    override fun getItemCount(): Int = prev.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val matchTitle = view.findViewById<TextView>(textViewMatch)
        val matchDate = view.findViewById<TextView>(textViewMatchDate)

        fun bindItem(prev: Events, listener: (Events) -> Unit) {
            matchTitle.text = "${prev.strHomeTeam} ${prev.intHomeScore} vs. ${prev.intAwayScore} ${prev.strAwayTeam}"
            matchDate.text = prev.strDate
            itemView.setOnClickListener { listener(prev) }
        }
    }
}