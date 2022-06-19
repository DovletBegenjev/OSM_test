package com.example.osmtest.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusStop_BusDao {
    @Insert
    fun insert(busStop_Bus: BusStop_Bus)

    @Update
    fun update(busStop_Bus: BusStop_Bus)

    @Delete
    fun delete(busStop_Bus: BusStop_Bus)

    @Query("SELECT bus.busId, bus.busNumber, bus_stop.busStopId FROM bus INNER JOIN bus_stop, busStop_Bus ON busStop_Bus.busId = bus.busId " +
            "AND busStop_Bus.busStopId = bus_stop.busStopId WHERE bus_stop.busStopId = :busStopId")
    fun getAllBusesForThisBusStop(busStopId: Long): List<Bus>

    @Query("SELECT bus_stop.* FROM bus_stop INNER JOIN bus, busStop_Bus ON busStop_Bus.busId = bus.busId " +
            "AND busStop_Bus.busStopId = bus_stop.busStopId WHERE bus.busNumber = :busNumber")
    fun getAllBusStopsForThisBus(busNumber: Int): List<BusStop>

    @Query("SELECT * FROM busStop_bus WHERE busStopId = :busStopId")
    fun getBusStop(busStopId: Long): LiveData<BusStop_Bus>

    @Query("SELECT * FROM busStop_bus ORDER BY busId ASC")
    fun getAllBus(): LiveData<List<BusStop_Bus>>

    @Query("SELECT * FROM busStop_bus ORDER BY busStopId ASC")
    fun getAllBusStop(): LiveData<List<BusStop_Bus>>
}