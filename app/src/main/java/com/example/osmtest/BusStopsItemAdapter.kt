package com.example.osmtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmtest.Model.BusStop
import com.example.osmtest.databinding.BusStopItemBinding

class BusStopsItemAdapter(private val busStops: List<BusStop>,
                          private val clickListener: (busStop: BusStop) -> Unit)
    : RecyclerView.Adapter<BusStopsItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val busStop = busStops[position]
        holder.bind(busStop, clickListener)
    }

    override fun getItemCount(): Int {
        return busStops.size
    }

    class ViewHolder(private val binding: BusStopItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BusStopItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(busStop: BusStop, clickListener: (busStop: BusStop) -> Unit) {
            binding.busStopName.text = busStop.busStopName
            binding.busStopLayout.setOnClickListener { clickListener(busStop) }
        }
    }
}