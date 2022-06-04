package com.example.osmtest

import android.util.Log
import androidx.lifecycle.*
import com.example.osmtest.Model.*
import kotlinx.coroutines.launch


class MapViewModel(
    val busStopDao: BusStopDao,
    val busDao: BusDao,
    val busStop_BusDao: BusStop_BusDao
): ViewModel() {
    /*var newBusStopName = ""
    var newBusStopLat = 0.0
    var newBusStopLon = 0.0

    var newBusNumber = 0

    private val busStops = busStopDao.getAll()
    val busStopsString = Transformations.map(busStops) {
        busStops -> formatBusStops(busStops)
    }
    private fun formatBusStops(busStops: List<BusStop>): String {
        return busStops.fold("") {
            str, item -> str + '\n' + "Name: ${item.busStopName}"
        }
    }

    private val buses = busDao.getAll()
    val busesString = Transformations.map(buses) {
        buses -> formatBuses(buses)
    }
    private fun formatBuses(buses: List<Bus>): String {
        return buses.fold("") {
            str, item -> str + '\n' + "Bus number: ${item.busNumber}"
        }
    }

    fun addBus(busNumber: Int) {
        viewModelScope.launch {
            val bus = Bus()
            bus.busNumber = busNumber
            busDao.insert(bus)
        }
    }*/

    val result = MutableLiveData<List<BusStop>>()
    fun getAllBusStops() {
        viewModelScope.launch {
            result.value = busStopDao.getAll2()
        }
    }

    val allBuses = MutableLiveData<List<Bus>>()
    fun getAllBuses() {
        viewModelScope.launch {
            allBuses.value = busDao.getAll()
        }
    }

    var busId = MutableLiveData<Int>()

    /*var busStopName = ""
    private var busNumbers = busStop_BusDao.getAllBusesForThisBusStop(busStopName)
    fun getBusNumbers(busStopName: String) {
        viewModelScope.launch {
            busNumbers = busStop_BusDao.getAllBusesForThisBusStop(busStopName)
            //Log.i("Permission", "${busNumbers.value}")
        }
    }
    val busNumbersString = Transformations.map(busNumbers) {
        busNumbers -> formatBusNumbers(busNumbers)
    }
    private fun formatBusNumbers(busNumbers: List<Bus>): String {
        Log.i("Permission", "${busNumbers}")
        return busNumbers.fold("") {
            str, item -> str + '\n' + item.busNumber
        }
    }*/
}