package com.example.osmtest.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus")
data class Bus(

    @PrimaryKey(autoGenerate = true)
    var busId: Long = 0L,

    var busNumber: Int = 0,
)
