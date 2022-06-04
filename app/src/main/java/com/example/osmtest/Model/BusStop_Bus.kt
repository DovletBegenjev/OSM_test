package com.example.osmtest.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "busStop_bus",
    foreignKeys = [ForeignKey(
        entity = BusStop::class,
        parentColumns = arrayOf("busStopId"),
        childColumns = arrayOf("busStopId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Bus::class,
        parentColumns = arrayOf("busId"),
        childColumns = arrayOf("busId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class BusStop_Bus(
    @PrimaryKey(autoGenerate = true)
    var busStop_BusId: Long = 0L,

    @ColumnInfo(index = true)
    var busStopId: Long = 0L,

    @ColumnInfo(index = true)
    var busId: Long = 0L
)
