package com.example.osmtest.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_stop")
data class BusStop(

    @PrimaryKey(autoGenerate = true)
    var busStopId: Long = 0L,

    var busStopName: String = "",

    var busStopLat: Double = 0.0,

    var busStopLon: Double = 0.0
)
