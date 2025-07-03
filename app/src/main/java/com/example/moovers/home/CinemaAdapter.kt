package com.example.moovers.home

class CinemaAdapter(private val cinemas: List<Cinema>) :
    RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder>() {

    inner class CinemaViewHolder(val binding: ItemCinemaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val binding = ItemCinemaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CinemaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        val cinema = cinemas[position]
        holder.binding.tvCinemaName.text = cinema.name
        holder.binding.tvDistance.text = cinema.distance
        holder.binding.tvHours.text = cinema.hours
        holder.binding.ivCinema.setImageResource(cinema.imageRes)
    }

    override fun getItemCount() = cinemas.size
}