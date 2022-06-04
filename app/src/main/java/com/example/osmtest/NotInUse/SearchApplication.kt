package com.example.osmtest.NotInUse

import android.app.Application
import com.example.osmtest.R
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.search.MapboxSearchSdk

class SearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapboxSearchSdk.initialize(
            application = this,
            accessToken = getString(R.string.mapbox_access_token),
            locationEngine = LocationEngineProvider.getBestLocationEngine(this)
        )
    }
}