package com.example.osmtest

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmtest.Model.Bus
import com.example.osmtest.databinding.BusRouteItemBinding

class BusRoutesItemAdapter(private val viewModel: MapViewModel,
                           private val buses: List<Bus>,
                           private val clickListener: (bus_number: Int) -> Unit)
    : RecyclerView.Adapter<BusRoutesItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bus = buses[position]
        Log.i("Tag3", "Bus: $bus")
        //
        val busStops = viewModel.busStop_BusDao.getAllBusStopsForThisBus(bus.busNumber)
        //
        var start = "start"
        var finish = "finish"
        if (busStops.isNotEmpty()) {
            start = busStops[0].busStopName
            finish = busStops[busStops.size - 1].busStopName
        }
        holder.bind(bus.busNumber, start, finish, clickListener)
    }

    override fun getItemCount(): Int {
        return buses.size
    }

    class ViewHolder(private val binding: BusRouteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BusRouteItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(bus_number: Int, bus_start: String, bus_finish: String,
                 clickListener: (bus_number: Int) -> Unit) {
            binding.apply {
                busNumber.text = bus_number.toString()
                busStart.text = bus_start
                busFinish.text = bus_finish
                busRouteLayout.setOnClickListener { clickListener(bus_number) }
            }
        }
    }
}