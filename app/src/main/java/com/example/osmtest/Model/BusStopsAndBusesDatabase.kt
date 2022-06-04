package com.example.osmtest.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BusStop::class, Bus::class, BusStop_Bus::class], version = 1, exportSchema = false)
abstract class BusStopsAndBusesDatabase : RoomDatabase() {
    abstract val busStopDao: BusStopDao
    abstract val busDao: BusDao
    abstract val busStop_Bus: BusStop_BusDao

    companion object {
        @Volatile
        private var INSTANCE: BusStopsAndBusesDatabase? = null

        fun getInstance(context: Context): BusStopsAndBusesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BusStopsAndBusesDatabase::class.java,
                        "busStopsAndBuses_database")
                        .createFromAsset("DB3")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}