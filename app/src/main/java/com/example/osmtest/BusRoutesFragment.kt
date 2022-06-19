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
import com.example.osmtest.Model.BusStopsAndBusesDatabase
import com.example.osmtest.databinding.FragmentBusRoutesBinding

class BusRoutesFragment : Fragment() {
    private var _binding: FragmentBusRoutesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBusRoutesBinding.inflate(inflater, container, false)
        val view = binding.root

        // View model
        val application = requireNotNull(this.activity).application

        val busStopDao = BusStopsAndBusesDatabase.getInstance(application).busStopDao
        val busDao = BusStopsAndBusesDatabase.getInstance(application).busDao
        val busStop_BusDao = BusStopsAndBusesDatabase.getInstance(application).busStop_Bus

        val viewModelFactory = MapViewModelFactory(busStopDao, busDao, busStop_BusDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

        //
        showAllBuses()

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.i("SearchApiExample", "onQueryTextSubmit: $p0")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.apply {
                    val query = "$p0%"
                    searchBus(query)
                    searchBusResult.observe(viewLifecycleOwner, Observer { result ->
                        result?.let {
                            if (result.isEmpty()) Toast.makeText(requireContext(), "No results found!", Toast.LENGTH_SHORT).show()
                            else if (binding.search.query == "") showAllBuses()
                            else {
                                val adapter = BusRoutesItemAdapter(viewModel, result) { busNumber -> selectedBus(busNumber)}
                                binding.busRoutesRv.adapter = adapter
                            }
                        }
                    })
                }
                return true
            }
        })

        return view
    }

    private fun showAllBuses() {
        viewModel.apply {
            getAllBuses()
            allBuses.observe(viewLifecycleOwner, Observer { allBuses ->
                allBuses?.let {
                    val adapter = BusRoutesItemAdapter(viewModel, allBuses) {busNumber -> selectedBus(busNumber)}
                    binding.busRoutesRv.adapter = adapter
                }
            })
        }
    }

    private fun selectedBus(busNumber: Int) {
        val action = BusRoutesFragmentDirections.actionBusRoutesFragmentToMapFragment(busNumber)
        view?.findNavController()?.navigate(action)
    }
}