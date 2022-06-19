package com.example.osmtest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.osmtest.Model.BusStop
import com.example.osmtest.Model.BusStopsAndBusesDatabase
import com.example.osmtest.databinding.FragmentBusStopsBinding

class BusStopsFragment : Fragment() {
    private var _binding: FragmentBusStopsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBusStopsBinding.inflate(inflater, container, false)
        val view = binding.root

        // View model
        val application = requireNotNull(this.activity).application

        val busStopDao = BusStopsAndBusesDatabase.getInstance(application).busStopDao
        val busDao = BusStopsAndBusesDatabase.getInstance(application).busDao
        val busStop_BusDao = BusStopsAndBusesDatabase.getInstance(application).busStop_Bus

        val viewModelFactory = MapViewModelFactory(busStopDao, busDao, busStop_BusDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

        //
        showAllBusStops()

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.i("SearchApiExample", "onQueryTextSubmit: $p0")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.apply {
                    val query = "$p0%"
                    searchBusStop(query)
                    searchBusStopResult.observe(viewLifecycleOwner, Observer { result ->
                        result?.let {
                            if (result.isEmpty()) Toast.makeText(requireContext(), "No results found!", Toast.LENGTH_SHORT).show()
                            else if (binding.search.query == "") showAllBusStops()
                            else {
                                val adapter = BusStopsItemAdapter(result) {busStopId -> selectedBusStop(busStopId)}
                                binding.busStopsRv.adapter = adapter
                            }
                        }
                    })
                }
                return true
            }
        })

        return view
    }

    private fun showAllBusStops() {
        viewModel.apply {
            getAllBusStops()
            allBusStops.observe(viewLifecycleOwner, Observer { busStops ->
                busStops?.let {
                    val adapter = BusStopsItemAdapter(busStops) {busStopId -> selectedBusStop(busStopId)}
                    binding.busStopsRv.adapter = adapter
                }
            })
        }
    }

    private fun selectedBusStop(busStop: BusStop) {
        val action = BusStopsFragmentDirections.actionBusStopsFragmentToMapFragment(-1,
            busStop.busStopId, busStop.busStopLat.toFloat(), busStop.busStopLon.toFloat(), busStop.busStopName)
        view?.findNavController()?.navigate(action)
    }
}