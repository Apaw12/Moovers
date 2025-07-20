package com.example.moovers.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moovers.R
import com.example.moovers.model.Ticket

class MyTicketAdapter(
    private var ticketList: List<Ticket>,
    private val onDelete: (Ticket) -> Unit
) : RecyclerView.Adapter<MyTicketAdapter.TicketViewHolder>() {

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
        holder.tvSeat.text = "Seat: ${ticket.seatNumber}"
        holder.tvDateTime.text = "${ticket.bookingDate} | ${ticket.bookingTime}"

        // Long Click untuk Delete
        holder.itemView.setOnLongClickListener {
            onDelete(ticket)
            true
        }
    }

    override fun getItemCount(): Int = ticketList.size

    fun updateTickets(newTickets: List<Ticket>) {
        ticketList = newTickets
        notifyDataSetChanged()
    }
}


