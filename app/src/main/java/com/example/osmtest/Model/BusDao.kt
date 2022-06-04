package com.example.osmtest.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusDao {
    @Insert
    fun insert(bus: Bus)

    @Update
    fun update(bus: Bus)

    @Delete
    fun delete(bus: Bus)

    @Query("SELECT * FROM bus WHERE busId = :busId")
    fun get(busId: Long): LiveData<Bus>

    @Query("SELECT * FROM bus ORDER BY busId")
    fun getAll(): List<Bus>
}