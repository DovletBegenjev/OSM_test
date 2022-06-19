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

    val allBusStops = MutableLiveData<List<BusStop>>()
    fun getAllBusStops() {
        viewModelScope.launch {
            allBusStops.value = busStopDao.getAll2()
        }
    }

    val allBuses = MutableLiveData<List<Bus>>()
    fun getAllBuses() {
        viewModelScope.launch {
            allBuses.value = busDao.getAll()
        }
    }

    val nearBusStops = MutableLiveData<List<BusStop>>()
    fun getNearBusStops(busStopLat: Double) {
        viewModelScope.launch {
            nearBusStops.value = busStopDao.findNearBusStops1(busStopLat)
        }
    }

    val searchBusStopResult = MutableLiveData<List<BusStop>>()
    fun searchBusStop(query: String) {
        viewModelScope.launch {
            searchBusStopResult.value = busStopDao.searchBusStop(query)
        }
    }

    val searchBusResult = MutableLiveData<List<Bus>>()
    fun searchBus(query: String) {
        viewModelScope.launch {
            searchBusResult.value = busDao.searchBus(query)
        }
    }

    /*val busStopAlongRoute = MutableLiveData<BusStop>()
    fun get(busStopLat: Double, busStopLon: Double) {
        viewModelScope.launch {
            busStopAlongRoute.value = busStopDao.get(busStopLat, busStopLon)
        }
    }*/

    var busId = MutableLiveData<Int>()

//    var busStopId = 0L
//    private var busNumbers = busStop_BusDao.getAllBusesForThisBusStop(busStopId)
    val busNumbers = MutableLiveData<List<Bus>>()
    fun getBusNumbers(busStopId: Long) {
        viewModelScope.launch {
            busNumbers.value = busStop_BusDao.getAllBusesForThisBusStop(busStopId)
            //Log.i("Permission", "${busNumbers.value}")
        }
    }
    /*val busNumbersString = Transformations.map(busNumbers) {
        busNumbers -> formatBusNumbers(busNumbers)
    }
    private fun formatBusNumbers(busNumbers: List<Bus>): String {
        Log.i("Permission", "${busNumbers}")
        return busNumbers.fold("") {
            str, item -> str + '\n' + item.busNumber
        }
    }*/
}