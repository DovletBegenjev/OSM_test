package com.example.osmtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.osmtest.Model.*
import java.lang.IllegalArgumentException

class MapViewModelFactory(private val busStopDao: BusStopDao,
                          private val busDao: BusDao,
                          private val busStop_BusDao: BusStop_BusDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java))
            return MapViewModel(busStopDao, busDao, busStop_BusDao) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }
}