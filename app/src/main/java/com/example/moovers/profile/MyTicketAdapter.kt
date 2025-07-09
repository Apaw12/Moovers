package com.example.moovers.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moovers.R
import com.example.moovers.model.TicketData

class MyTicketAdapter(private val ticketList: List<TicketData>) :
    RecyclerView.Adapter<MyTicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val tvSeat: TextView = itemView.findViewById(R.id.tvSeat)
        val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = ticketList[position]
        holder.tvTitle.text = ticket.movieTitle
        holder.tvSeat.text = "Seat: ${ticket.seat}"
        holder.tvDateTime.text = "${ticket.date} | ${ticket.time}"
    }

    override fun getItemCount(): Int = ticketList.size
}
