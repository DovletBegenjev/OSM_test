package com.example.osmtest.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusStopDao {
    @Insert
    fun insert(busStop: BusStop)

    @Update
    fun update(busStop: BusStop)

    @Delete
    fun delete(busStop: BusStop)

    /*@Query("SELECT * FROM bus_stop WHERE busStopLat = :busStopLat AND busStopLon = :busStopLon")
    fun get(busStopLat: Double, busStopLon: Double): BusStop*/

    @Query("SELECT * FROM bus_stop ORDER BY busStopId ASC")
    fun getAll(): LiveData<List<BusStop>>

    @Query("SELECT * FROM bus_stop")
    fun getAll2(): List<BusStop>

    @Query("UPDATE bus_stop SET busStopName = :busStopName WHERE busStopLat = :busStopLat AND busStopLon = :busStopLon")
    fun insertBusStopName(busStopName: String, busStopLat: Double, busStopLon: Double)
}