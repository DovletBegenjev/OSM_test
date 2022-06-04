package com.example.osmtest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.osmtest.databinding.FragmentNavigationBinding
import retrofit2.HttpException
import java.io.IOException

class NavigationFragment : Fragment() {
    private var _binding: FragmentNavigationBinding? = null
    private val binding get() = _binding!!

    private lateinit var fromAddress: Address
    private lateinit var toAddress: Address

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        val view = binding.root

        val fromDestinationName = NavigationFragmentArgs.fromBundle(requireArguments()).fromDestinationName
        val fromLat = NavigationFragmentArgs.fromBundle(requireArguments()).fromDestinationLat
        val fromLon = NavigationFragmentArgs.fromBundle(requireArguments()).fromDestinationLon

        binding.searchFromDestination.setQuery(fromDestinationName, false)

        val adapterForFromQuery = SuggestionItemAdapter { address -> putFromQuery(address) }
        binding.searchFromDestination.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.i("SearchApiExample", "onQueryTextSubmit: $p0")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                lifecycleScope.launchWhenCreated {
                    binding.progressBar.isVisible = true
                    val response = try {
                        p0?.replace("[", "")
                        p0?.replace("]", "")
                        val query = "{$p0}"
                        Log.e("SearchApiExample", "$query")
                        RetrofitInstance.api.getAddress(query)
                    } catch (e: IOException) {
                        Log.i("SearchApiExample", "IOException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        Log.i("SearchApiExample", "HttpException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    }

                    if (response.isSuccessful && response.body() != null) {
                        Log.e("SearchApiExample", "response.isSuccessful")
                        adapterForFromQuery.submitList(response.body())
                        binding.suggestions.adapter = adapterForFromQuery
                    } else {
                        Log.i("SearchApiExample", "Response is not successful!")
                    }
                    binding.progressBar.isVisible = false
                }
                return true
            }
        })

        val adapterForToQuery = SuggestionItemAdapter { address -> passToQuery(address) }
        binding.searchToDestination.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                // NOTHING TO DO
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                lifecycleScope.launchWhenCreated {
                    binding.progressBar.isVisible = true
                    val response = try {
                        p0?.replace("[", "")
                        p0?.replace("]", "")
                        val query = "{$p0}"
                        Log.e("SearchApiExample", "$query")
                        RetrofitInstance.api.getAddress(query)
                    } catch (e: IOException) {
                        Log.i("SearchApiExample", "IOException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        Log.i("SearchApiExample", "HttpException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    }

                    if (response.isSuccessful && response.body() != null) {
                        Log.e("SearchApiExample", "response.isSuccessful")
                        adapterForToQuery.submitList(response.body())
                        binding.suggestions.adapter = adapterForToQuery
                    } else {
                        Log.i("SearchApiExample", "Response is not successful!")
                    }
                    binding.progressBar.isVisible = false
                }
                return true
            }
        })

        return view
    }

    private fun putFromQuery(address: Address) {
        fromAddress = address
        val adr = address.display_name.split(",".toRegex(), 3)
        var splitAddress = ""
        for (i in adr.indices) {
            splitAddress += adr[i]
        }
        binding.searchFromDestination.setQuery(splitAddress, false)
    }

    private fun passToQuery(address: Address) {
        toAddress = address
        val action = NavigationFragmentDirections.actionSuggestionsFragmentToMapFragment(
            fromAddress.display_name,
            fromAddress.lat.toFloat(),
            fromAddress.lon.toFloat(),
            toAddress.display_name,
            toAddress.lat.toFloat(),
            toAddress.lon.toFloat()
        )
        findNavController().navigate(action).also {
            MapFragment()
        }
    }
}