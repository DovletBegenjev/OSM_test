package com.example.osmtest.NotInUse

import android.location.Location
import android.location.LocationListener
import com.example.osmtest.MapFragment
import com.example.osmtest.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MyLocationListener : LocationListener {
    override fun onLocationChanged(location: Location) {
        val currentLocation = GeoPoint(location)
        val mapView = MapFragment().activity?.findViewById<MapView>(R.id.mapView)
        val mapController = mapView?.controller
        mapController?.setCenter(currentLocation)
        mapController?.animateTo(currentLocation)
    }
}