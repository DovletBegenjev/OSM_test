package com.example.osmtest

/*import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue*/
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.osmtest.Model.BusStop
import com.example.osmtest.Model.BusStopsAndBusesDatabase
import com.example.osmtest.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer
import org.osmdroid.bonuspack.location.NominatimPOIProvider
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import kotlin.math.sqrt


class MapFragment : Fragment(), MapEventsReceiver {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var location: Location
    private lateinit var startPoint: GeoPoint
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mapController: MapController

    private lateinit var fromAddress: Address
    private lateinit var toAddress: Address

    private var nearFromBusStops: ArrayList<GeoPoint> = arrayListOf()
    private var nearToBusStops: ArrayList<GeoPoint> = arrayListOf()

    private lateinit var start: GeoPoint
    private lateinit var finish: GeoPoint

    private lateinit var viewModel: MapViewModel

    /*private val URL = "https://database-22465-default-rtdb.europe-west1.firebasedatabase.app/"
    private val db = Firebase.database(URL)
    private val busStopTableRef = db.getReference("bus_stops")*/

    // -------------------------------------------------------------------------------------------//

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        startPoint = GeoPoint(0, 0)

        // TODO("REMOVE THIS AND USE COROUTINES, YOU BASTARD!!!")
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        // TODO("REMOVE THIS AND USE COROUTINES, YOU BASTARD!!!")

        getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        //getInstance().userAgentValue = "MyOwnUserAgent/1.0"

        // Building map
        //binding.mapView.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT)
        mapController = binding.mapView.controller as MapController
        val provider = MapTileProviderBasic(context)
        provider.tileSource = TileSourceFactory.MAPNIK
        val tilesOverlay = TilesOverlay(provider, context)
        binding.mapView.overlays.add(tilesOverlay)

        // Needed for pinch zooms
        binding.mapView.setMultiTouchControls(true)
        //binding.mapView.setBuiltInZoomControls(true)

        //
        binding.mapView.minZoomLevel = 3.0
        binding.mapView.isVerticalMapRepetitionEnabled = false
        val boundingBox = BoundingBox(85.0, 0.0, -85.0, 0.0)
        binding.mapView.setScrollableAreaLimitLatitude(boundingBox.actualNorth, boundingBox.actualSouth, binding.mapView.height)

        // On screen compass
        val compassOverlay = CompassOverlay(
            requireContext(),
            InternalCompassOrientationProvider(requireContext()),
            binding.mapView
        )
        compassOverlay.enableCompass()
        binding.mapView.overlays.add(compassOverlay)

        val _displayMetrics = activity?.resources?.displayMetrics
        val displayMetrics = _displayMetrics!!

        // Map scale
        val scaleBarOverlay = ScaleBarOverlay(binding.mapView)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(displayMetrics.widthPixels / 2, 10)
        binding.mapView.overlays.add(scaleBarOverlay)

        // Copyright overlay
        val copyrightOverlay = CopyrightOverlay(requireContext())
        binding.mapView.overlays.add(copyrightOverlay)

        // Support for map rotation
        /*val rotationGestureOverlay = RotationGestureOverlay(binding.mapView)
        rotationGestureOverlay.isEnabled = true
        binding.mapView.overlays.add(rotationGestureOverlay)*/

        // Scales tiles to the current screen's DPI, helps with readability of labels
        //binding.mapView.isTilesScaledToDpi = true

        // View model
        val application = requireNotNull(this.activity).application

        val busStopDao = BusStopsAndBusesDatabase.getInstance(application).busStopDao
        val busDao = BusStopsAndBusesDatabase.getInstance(application).busDao
        val busStop_BusDao = BusStopsAndBusesDatabase.getInstance(application).busStop_Bus

        val viewModelFactory = MapViewModelFactory(busStopDao, busDao, busStop_BusDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

        binding.viewModel = viewModel

        //
        val mapEventsOverlay = MapEventsOverlay(this)
        binding.mapView.overlays.add(0, mapEventsOverlay)

        //
        val adapterForFromQuery = SuggestionItemAdapter { address -> putMarker(address) }
        binding.searchFromDestination.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.i("SearchApiExample", "onQueryTextSubmit: $p0")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                lifecycleScope.launchWhenCreated {
                    binding.progressBar.isVisible = true
                    val response = try {
                        p0?.replace("[", "")
                        p0?.replace("]", "")
                        val query = "{$p0}"
                        Log.e("SearchApiExample", "$query")
                        RetrofitInstance.api.getAddress(query)
                    } catch (e: IOException) {
                        Log.i("SearchApiExample", "IOException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        Log.i("SearchApiExample", "HttpException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    }

                    if (response.isSuccessful && response.body() != null) {
                        Log.e("SearchApiExample", "response.isSuccessful")
                        adapterForFromQuery.submitList(response.body())
                        binding.suggestions.adapter = adapterForFromQuery
                    } else {
                        Log.i("SearchApiExample", "Response is not successful!")
                    }
                    binding.progressBar.isVisible = false
                }
                return true
            }
        })

        val adapterForToQuery = SuggestionItemAdapter { address -> buildRoute(address) }
        binding.searchToDestination.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                // NOTHING TO DO
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                lifecycleScope.launchWhenCreated {
                    binding.progressBar.isVisible = true
                    val response = try {
                        //p0?.replace("[", "")
                        //p0?.replace("]", "")
                        val query = "$p0"
                        Log.e("SearchApiExample", "$query")
                        RetrofitInstance.api.getAddress(query)
                    } catch (e: IOException) {
                        Log.i("SearchApiExample", "IOException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        Log.i("SearchApiExample", "HttpException: $e")
                        binding.progressBar.isVisible = false
                        return@launchWhenCreated
                    }

                    if (response.isSuccessful && response.body() != null) {
                        Log.e("SearchApiExample", "response.isSuccessful")
                        adapterForToQuery.submitList(response.body())
                        binding.suggestions.adapter = adapterForToQuery
                    } else {
                        Log.i("SearchApiExample", "Response is not successful!")
                    }
                    binding.progressBar.isVisible = false
                }
                return true
            }
        })

        // Suggestions panel on start
        val plainArray = ArrayList<String>()
        plainArray.add("")
        val onStartAdapter = SuggestionItemAdapter {}
        binding.suggestions.adapter = onStartAdapter

        //
        binding.searchFromDestination.setOnClickListener {
            binding.slidingPanel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }

        //viewModel.getAllBusStops()
        /*viewModel.result.observe(viewLifecycleOwner, Observer {
            it?.let {
                for (i in 500 until it.size) {
                    Log.i("Permission", "{\"busStopName\": \"\", " +
                            "\"busStopLat\": ${it[i].busStopLat}, \"busStopLon\": ${it[i].busStopLon}},")
                }
                Log.i("Permission", "${it.size}")
            }
        })*/

        //
        binding.bottomBar.setOnItemSelectedListener {
            if (it.itemId == R.id.search) {
                binding.dragView.visibility = View.VISIBLE
                binding.slidingPanel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
            if (it.itemId == R.id.bus_routes) {
                val action = MapFragmentDirections.actionMapFragmentToBusRoutesFragment()
                view.findNavController().navigate(action)
            }
            if (it.itemId == R.id.bus_stops) {
                val action = MapFragmentDirections.actionMapFragmentToBusStopsFragment()
                view.findNavController().navigate(action)
            }
            return@setOnItemSelectedListener true
        }

        val busNumber = MapFragmentArgs.fromBundle(requireArguments()).busNumber
        // Показать маршрут выбранного автобуса на карте
        if (busNumber != -1) showBusRouteOnMap(busNumber)

        val busStopId = MapFragmentArgs.fromBundle(requireArguments()).busStopId
        val busStopLat = MapFragmentArgs.fromBundle(requireArguments()).busStopLat.toDouble()
        val busStopLon = MapFragmentArgs.fromBundle(requireArguments()).busStopLon.toDouble()
        val busStopName = MapFragmentArgs.fromBundle(requireArguments()).busStopName
        // Показать автобусы, проезжающие через выбранную автобусную остановку
        if (busStopId != -1L) showBusesOnThisBusStop(busStopId, busStopLat, busStopLon, busStopName, viewModel)

        // My location
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), binding.mapView)
        locationOverlay.enableMyLocation()
        locationOverlay.isDrawAccuracyEnabled = true
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()
        binding.mapView.overlays.add(locationOverlay)

        binding.mapView.invalidate()

        return view
    }

    private fun showBusesOnThisBusStop(busStopId: Long, busStopLat: Double, busStopLon: Double,
                                       busStopName: String, viewModel: MapViewModel) {
        // FIXME: move this to view model
        val buses = viewModel.busStop_BusDao.getAllBusesForThisBusStop(busStopId)
        //

        startPoint = GeoPoint(busStopLat, busStopLon)
        mapController.animateTo(startPoint)
        mapController.setCenter(startPoint)

        binding.apply {
            slidingPanel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            searchToDestination.visibility = View.GONE
            searchFromDestination.visibility = View.GONE
            slidingPanel.panelHeight = 500

            bottomBar.visibility = View.GONE
        }

        val busStopIcon = resources.getDrawable(R.drawable.marker_poi_flickr)
        val poiMarker = Marker(binding.mapView)
        poiMarker.apply {
            title = busStopName
            var busNumbersString = "Awtobuslar:\n"
            for (i in buses) {
                busNumbersString += "${i.busNumber}\n"
            }
            snippet = busNumbersString
            position = GeoPoint(busStopLat, busStopLon)
            icon = busStopIcon
        }

        val poiMarkers = RadiusMarkerClusterer(requireContext())
        poiMarkers.add(poiMarker)
        binding.mapView.overlays.add(poiMarkers)
        binding.mapView.invalidate()

        val adapter = BusRoutesItemAdapter(viewModel, buses) { busNumber ->
            showBusRouteOnMap(busNumber).also {
                binding.apply {
                    mapView.overlays.remove(poiMarkers)
                    slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
            }
        }
        binding.suggestions.adapter = adapter

        //
        binding.apply {
            closeButton.setOnClickListener {
                searchFromDestination.visibility = View.VISIBLE
                searchToDestination.visibility = View.VISIBLE
                mapView.overlays.remove(poiMarkers)
                slidingPanel.panelHeight = 0
                slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                bottomBar.visibility = View.VISIBLE
            }
        }
    }

    private fun showBusRouteOnMap(busNumber: Int) {
        binding.apply {
            slidingPanel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            searchToDestination.visibility = View.GONE
            searchFromDestination.visibility = View.GONE
            slidingPanel.panelHeight = 500

            bottomBar.visibility = View.GONE
        }

        val roadManager = OSRMRoadManager(requireContext(), "MyOwnUserAgent/1.0")
        val wayPoints = ArrayList<GeoPoint>()
        val busStopMarkers = RadiusMarkerClusterer(requireContext())
        viewModel.apply {
            // FIXME: move this to view model
            val busStops = busStop_BusDao.getAllBusStopsForThisBus(busNumber)
            //

            //
            val pos = busStops.size - busStops.size / 2
            startPoint = GeoPoint(busStops[pos].busStopLat, busStops[pos].busStopLon)
            mapController.animateTo(startPoint)
            mapController.setCenter(startPoint)
            mapController.setZoom(14)

            for (busStop in busStops) {
                // FIXME: move this to view model
                val buses = viewModel.busStop_BusDao.getAllBusesForThisBusStop(busStop.busStopId)
                //
                wayPoints.add(GeoPoint(busStop.busStopLat, busStop.busStopLon))

                val busStopIcon = resources.getDrawable(R.drawable.marker_poi_default)
                val poiMarker = Marker(binding.mapView)
                poiMarker.apply {
                    title = busStop.busStopName
                    var busNumbersString = "Awtobuslar:\n"
                    for (i in buses) {
                        busNumbersString += "${i.busNumber}\n"
                    }
                    snippet = busNumbersString
                    position = GeoPoint(busStop.busStopLat, busStop.busStopLon)
                    icon = busStopIcon
                }
                busStopMarkers.add(poiMarker)
            }
            val road = roadManager.getRoad(wayPoints)
            val roadOverlay = RoadManager.buildRoadOverlay(road)
            roadManager.setMean(OSRMRoadManager.MEAN_BY_CAR)
            if (wayPoints.isNotEmpty()) {
                roadOverlay.outlinePaint.color = Color.BLUE
                binding.mapView.overlays.add(roadOverlay)
            }
            wayPoints.clear()

            val adapter = BusStopsItemAdapter(busStops) {busStop ->
                showBusesOnThisBusStop(busStop.busStopId, busStop.busStopLat, busStop.busStopLon,
                busStop.busStopName, viewModel).also {
                    binding.apply {
                        mapView.overlays.remove(busStopMarkers)
                        mapView.overlays.remove(roadOverlay)
                        slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }
                }
            }
            binding.suggestions.adapter = adapter

            binding.mapView.overlays.add(busStopMarkers)
            binding.mapView.invalidate()

            //
            binding.apply {
                closeButton.setOnClickListener {
                    mapView.overlays.remove(roadOverlay)
                    mapView.overlays.remove(busStopMarkers)
                    searchFromDestination.visibility = View.VISIBLE
                    searchToDestination.visibility = View.VISIBLE
                    slidingPanel.panelHeight = 0
                    slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    bottomBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun markerClick(poiMarker: Marker, file: File, busStopId: Long, busId: Int) {
        poiMarker.icon = resources.getDrawable(R.drawable.moreinfo_arrow_pressed)
        binding.addButton.setBackgroundColor(Color.GREEN)
        binding.addButton.setOnClickListener {
            //if (binding.searchFromDestination.query.isNotEmpty()) {
                if (checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    val name = binding.searchFromDestination.query.toString()
                    try {
                        val text = "INSERT INTO busStop_Bus(busId, busStopId) VALUES('${busId + 1}', '$busStopId');\n"
                        file.appendText(text)
                        Toast.makeText(requireContext(), "File is saved!", Toast.LENGTH_SHORT).show()
                        Log.i("Permission", "INSERT INTO busStop_Bus(busId, busStopId) VALUES('${busId + 1}', '$busStopId')")
                        poiMarker.icon = resources.getDrawable(R.drawable.marker_poi_flickr)
                    } catch (e: IOException) {
                        Log.i("Permission", "File error: $e")
                    }
                } else {
                    Toast.makeText(requireContext(), "Cannot write to external storage!", Toast.LENGTH_SHORT).show()
                }
            //}
            //else Toast.makeText(requireContext(), "Введи имя, дубина!", Toast.LENGTH_SHORT).show()
            binding.addButton.setOnClickListener(null)
            binding.addButton.setBackgroundColor(Color.RED)
        }
    }

    fun checkPermission(permission: String): Boolean {
        val check = ContextCompat.checkSelfPermission(requireContext(), permission)
        return(check == PackageManager.PERMISSION_GRANTED)
    }

    private fun buildRoute2() {
        val roadManager = OSRMRoadManager(requireContext(), "MyOwnUserAgent/1.0")
        val wayPoints = ArrayList<GeoPoint>()

        var size = if (nearFromBusStops.size > nearToBusStops.size) nearToBusStops.size
                   else nearFromBusStops.size

        for (i in 0 until size) {
            // start point
            wayPoints.add(nearFromBusStops[i])
            // stop point
            wayPoints.add(nearToBusStops[i])

            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)
            val road = roadManager.getRoad(wayPoints)
            val roadOverlay = RoadManager.buildRoadOverlay(road)
            binding.mapView.overlays.add(roadOverlay)
        }
    }

    private fun buildRoute(address: Address) {
        toAddress = address

        val fromPoint = GeoPoint(fromAddress.lat.toDouble(), fromAddress.lon.toDouble())
        if (fromPoint == startPoint) putMarker(fromAddress)

        val markerIcon = resources.getDrawable(R.drawable.marker_default)
        val marker = Marker(binding.mapView)
        val InfoWindow = MarkerInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, binding.mapView)
        val point = GeoPoint(address.lat.toDouble(), address.lon.toDouble())

        marker.apply {
            position = point
            icon = markerIcon
            title = "${address.lat}, ${address.lon}"
            //snippet = address.type
            //infoWindow = InfoWindow
        }

        binding.mapView.overlays.remove(marker)
        binding.mapView.overlays.add(marker)

        //
        val poiProvider = NominatimPOIProvider(BuildConfig.APPLICATION_ID)
        val busStops = poiProvider.getPOICloseTo(point, "bus_stop", 5, 0.01)
        val poiMarkers = RadiusMarkerClusterer(requireContext())

        val roadManager = OSRMRoadManager(requireContext(), "MyOwnUserAgent/1.0")
        val wayPoints = ArrayList<GeoPoint>()

        val startPoint = GeoPoint(toAddress.lat.toDouble(), toAddress.lon.toDouble())
        wayPoints.add(startPoint)
        /*for (bus_stop in busStops) {
            var busStopIcon = resources.getDrawable(R.drawable.marker_poi_default)
            val poiMarker = Marker(binding.mapView)
            poiMarker.apply {
                title = bus_stop.mId.toString()
                snippet = bus_stop.mCategory
                position = bus_stop.mLocation
                icon = busStopIcon
            }

            wayPoints.add(bus_stop.mLocation)

            //roadManager.setMean(OSRMRoadManager.MEAN_BY_CAR)
            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)

            val road = roadManager.getRoad(wayPoints)

            if (road.mLength < 1) nearToBusStops.add(bus_stop.mLocation)

            poiMarkers.add(poiMarker)
            wayPoints.remove(bus_stop.mLocation)
        }*/

        var arrOfBusStops = findNearBusStops(toAddress, 0.006)
        if (arrOfBusStops.isEmpty()) {
            arrOfBusStops = findNearBusStops(toAddress, 0.01)
        }
        if (arrOfBusStops.isEmpty()) {
            arrOfBusStops = findNearBusStops(toAddress, 0.02)
        }
        if (arrOfBusStops.isEmpty()) {
            Toast.makeText(requireContext(), "В ближайших 2 километрах не найдено автобусных остановок. " +
                    "Попробуйте изменить зону поиска.", Toast.LENGTH_LONG).show()
        }

        Log.i("Permission", "To size: ${arrOfBusStops.size}, $arrOfBusStops")

        var distance = 20.0
        var nearestBusStops: ArrayList<BusStop> = arrayListOf()
        for (i in arrOfBusStops) {
            val location = GeoPoint(i.busStopLat, i.busStopLon)
            var busStopIcon = resources.getDrawable(R.drawable.marker_poi_default)
            val poiMarker = Marker(binding.mapView)
            poiMarker.apply {
                title = i.busStopName
                snippet = i.busStopId.toString()
                position = location
                icon = busStopIcon
            }

            wayPoints.add(location)
            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)
            val road = roadManager.getRoad(wayPoints)
            Log.i("Permission", "Length for ${i.busStopId} = ${road.mLength}")

            //if (road.mLength < 1.5) nearToBusStops.add(location)
            if (road.mLength < distance) {
                distance = road.mLength

                // Местоположение остановки, которая ближе всего к точке Б
                nearToBusStops.clear()
                nearToBusStops.add(location)

                // Остановка, которая ближе всего к точке Б
                nearestBusStops.clear()
                nearestBusStops.add(i)
            }

            //poiMarkers.add(poiMarker)
            wayPoints.remove(location)
        }

        // Ближайшие к точке А остановки, через которые проезжают автобусы,
        // которые едут прямо до пункта назначения
        val nearestBusStop = findNearStraightBusStops(nearestBusStops)
        Log.i("Tag3", "$nearestBusStop")

        mapController.setZoom(18)
        mapController.animateTo(point)
        mapController.setCenter(point)
        binding.slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        binding.searchToDestination.clearFocus()

        if (nearFromBusStops.isNotEmpty()) {
            for (i in nearToBusStops.indices) {
                // stop point
                wayPoints.add(nearToBusStops[i])

                roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)
                val road = roadManager.getRoad(wayPoints)
                val roadOverlay = RoadManager.buildRoadOverlay(road)
                binding.mapView.overlays.add(roadOverlay)
                wayPoints.remove(nearToBusStops[i])
                finish = nearToBusStops[i]
            }

            wayPoints.clear()
            wayPoints.add(start)
            wayPoints.add(finish)
            roadManager.setMean(OSRMRoadManager.MEAN_BY_CAR)
            val road = roadManager.getRoad(wayPoints)
            val roadOverlay = RoadManager.buildRoadOverlay(road)
            //roadOverlay.outlinePaint.pathEffect = DashPathEffect(floatArrayOf(10f, 20f), 0f)
            roadOverlay.outlinePaint.color = Color.GREEN
            binding.mapView.overlays.add(roadOverlay)

            /*val busStops = poiProvider.getPOIAlong(road.routeLow, "bus_stop", 50, 1.0)
            viewModel.apply {
                for (bus_stop in busStops) {
                    get(bus_stop.mLocation.latitude, bus_stop.mLocation.longitude)

                    busStopAlongRoute.observe(viewLifecycleOwner, Observer { busStopAlongRoute ->
                        busStopAlongRoute?.let {
                            var busStopIcon = resources.getDrawable(R.drawable.marker_poi_flickr)
                            val poiMarker = Marker(binding.mapView)
                            poiMarker.apply {
                                title = busStopAlongRoute.busStopName

                                // TODO("Use view model")
                                val busNumbers = busStop_BusDao.getAllBusesForThisBusStop(busStopAlongRoute.busStopId)
                                var busNumbersString = "Awtobuslar:\n"
                                for (i in busNumbers) {
                                    busNumbersString += "${i.busNumber}\n"
                                }
                                snippet = busNumbersString
                                // TODO("Use view model")

                                val busStopPosition = GeoPoint(busStopAlongRoute.busStopLat, busStopAlongRoute.busStopLon)
                                position = busStopPosition
                                icon = busStopIcon
                            }
                            poiMarkers.add(poiMarker)
                        }
                    })
                }
            }*/

            Log.i("Permission", "nearToBusStops: $nearToBusStops")

            binding.mapView.overlays.add(poiMarkers)
            binding.mapView.invalidate()
        } else {
            binding.mapView.invalidate()
            return
        }
        binding.mapView.overlays.add(poiMarkers)
        binding.mapView.invalidate()
        //buildRoute2()
    }

    // -------------------------------------------------------------------------------------------//

    private fun putMarker(address: Address) {
        fromAddress = address
        val markerIcon = resources.getDrawable(R.drawable.marker_default)
        val marker = Marker(binding.mapView)
        val InfoWindow = MarkerInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, binding.mapView)
        val point = GeoPoint(address.lat.toDouble(), address.lon.toDouble())
        /*var addressName = address.display_name
        addressName = addressName.split(",")[0]*/
        marker.apply {
            position = point
            icon = markerIcon
            title = "${address.lat}, ${address.lon}"
            //snippet = address.type
            //infoWindow = InfoWindow
        }

        binding.mapView.overlays.remove(marker)
        binding.mapView.overlays.add(marker)
        Log.i("Permission", "fromAddress: $point")

        //
        val poiProvider = NominatimPOIProvider(BuildConfig.APPLICATION_ID)
        val poiMarkers = RadiusMarkerClusterer(requireContext())

        val roadManager = OSRMRoadManager(requireContext(), "MyOwnUserAgent/1.0")
        val wayPoints = ArrayList<GeoPoint>()

        val startPoint = GeoPoint(fromAddress.lat.toDouble(), fromAddress.lon.toDouble())
        wayPoints.add(startPoint)
        /*for (bus_stop in busStops) {
            var busStopIcon = resources.getDrawable(R.drawable.marker_poi_default)
            val poiMarker = Marker(binding.mapView)
            poiMarker.apply {
                title = bus_stop.mId.toString()
                snippet = bus_stop.mCategory
                position = bus_stop.mLocation
                icon = busStopIcon
            }

            wayPoints.add(bus_stop.mLocation)

            //roadManager.setMean(OSRMRoadManager.MEAN_BY_CAR)
            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)

            val road = roadManager.getRoad(wayPoints)
            Log.i("Permission", "Length for ${bus_stop.mId} = ${road.mLength}")

            if (road.mLength < 1.5) nearFromBusStops.add(bus_stop.mLocation)

            poiMarkers.add(poiMarker)
            wayPoints.remove(bus_stop.mLocation)
        }*/

        var arrOfBusStops = findNearBusStops(fromAddress, 0.006)
        if (arrOfBusStops.isEmpty()) {
            /*Toast.makeText(requireContext(), "В ближайших 500 метрах не найдено автобусных остановок." +
                    "Зона поиска увеличена до 1 километра.", Toast.LENGTH_SHORT).show()*/
            arrOfBusStops = findNearBusStops(fromAddress, 0.01)
        }
        if (arrOfBusStops.isEmpty()) {
            /*Toast.makeText(requireContext(), "В ближайшем километре не найдено автобусных остановок." +
                    "Зона поиска увеличена до 1.5 километров.", Toast.LENGTH_SHORT).show()*/
            arrOfBusStops = findNearBusStops(fromAddress, 0.02)
        }
        if (arrOfBusStops.isEmpty()) {
            Toast.makeText(requireContext(), "В ближайших 2 километрах не найдено автобусных остановок. " +
                    "Попробуйте изменить зону поиска.", Toast.LENGTH_LONG).show()
        }
        Log.i("Permission", "From size: ${arrOfBusStops.size}, $arrOfBusStops")
        var distance = 20.0
        for (i in arrOfBusStops) {
            val location = GeoPoint(i.busStopLat, i.busStopLon)
            var busStopIcon = resources.getDrawable(R.drawable.marker_poi_default)
            val poiMarker = Marker(binding.mapView)
            poiMarker.apply {
                title = i.busStopName
                snippet = i.busStopId.toString()
                position = location
                icon = busStopIcon
            }

            wayPoints.add(location)
            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)
            val road = roadManager.getRoad(wayPoints)
            Log.i("Permission", "Length for ${i.busStopId} = ${road.mLength}")

            //if (road.mLength < 1.5) nearFromBusStops.add(location)
            if (road.mLength < distance) {
                distance = road.mLength
                nearFromBusStops.clear()
                nearFromBusStops.add(location)
            }

            //poiMarkers.add(poiMarker)
            wayPoints.remove(location)
        }

        mapController.setZoom(18)
        mapController.animateTo(point)
        mapController.setCenter(point)
        binding.slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        binding.searchFromDestination.clearFocus()

        if (nearFromBusStops.isNotEmpty()) {
            binding.mapView.overlays.add(poiMarkers)
            for (i in nearFromBusStops.indices) {
                // stop point
                wayPoints.add(nearFromBusStops[i])
                roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)
                val road = roadManager.getRoad(wayPoints)
                val roadOverlay = RoadManager.buildRoadOverlay(road)
                binding.mapView.overlays.add(roadOverlay)
                wayPoints.remove(nearFromBusStops[i])
                start = nearFromBusStops[i]
            }
            Log.i("Permission", "nearFromBusStops: $nearFromBusStops")
            binding.mapView.invalidate()
        } else {
            binding.mapView.invalidate()
            return
        }
    }

    // Найти любые ближайшие автобусные остановки
    private fun findNearBusStops(address: Address, distanceOfSearch: Double): ArrayList<BusStop> {
        var arrOfBusStops = arrayListOf<BusStop>()
        viewModel.apply {
            allBusStops.observe(viewLifecycleOwner, Observer { busStops ->
                busStops?.let {
                    for (bus_stop in busStops) {
                        // (lat1 - lat2)^2
                        val x = address.lat.toDouble() * address.lat.toDouble() -
                                2 * address.lat.toDouble() * bus_stop.busStopLat +
                                bus_stop.busStopLat * bus_stop.busStopLat

                        // (lon1 - lon2)^2
                        val y = address.lon.toDouble() * address.lon.toDouble() -
                                2 * address.lon.toDouble() * bus_stop.busStopLon +
                                bus_stop.busStopLon * bus_stop.busStopLon

                        // sqrt((lat1 - lat2)^2 + (lon1 - lon2)^2)
                        val distance = sqrt(x + y)

                        if (distance < distanceOfSearch) {
                            arrOfBusStops.add(bus_stop)
                            Log.i("Permission", "distance = $distance")
                        }
                    }
                }
            })
        }
        return arrOfBusStops
    }

    // Найти ближайшую к точке А остановку, через которую проезжает автобус,
    // который напрямую едет до точки назначение
    private fun findNearStraightBusStops(nearestBusStops: ArrayList<BusStop>): ArrayList<BusStop> {
        val poiMarkers = RadiusMarkerClusterer(requireContext())
        val roadManager = OSRMRoadManager(requireContext(), "MyOwnUserAgent/1.0")
        val wayPoints = ArrayList<GeoPoint>()
        wayPoints.add(start)
        viewModel.apply {
            // TODO("Use view model")
            val busNumbers = busStop_BusDao.getAllBusesForThisBusStop(nearestBusStops[0].busStopId)
            nearestBusStops.clear()
            for ((counter, busNumber) in busNumbers.withIndex()) {
                var min = 999.0
                //Log.i("Tag3", "${busNumber.busNumber}\n")
                val busStops = busStop_BusDao.getAllBusStopsForThisBus(busNumber.busNumber)
                for (busStop in busStops) {
                    // (lat1 - lat2)^2
                    val x = busStop.busStopLat * busStop.busStopLat -
                            2 * busStop.busStopLat * start.latitude +
                            start.latitude * start.latitude

                    // (lon1 - lon2)^2
                    val y = busStop.busStopLon * busStop.busStopLon -
                            2 * busStop.busStopLon * start.longitude +
                            start.longitude * start.longitude

                    // sqrt((lat1 - lat2)^2 + (lon1 - lon2)^2)
                    val distance = sqrt(x + y)
                    if (distance < min) {
                        min = distance
                        if (nearestBusStops.size <= counter) nearestBusStops.add(busStop)
                        else nearestBusStops[counter] = busStop
                    }
                }
            }
            // TODO("Use view model")
        }

        Log.i("Tag3", "$nearestBusStops")

        var min = 999.0
        var nearestBusStop = arrayListOf<BusStop>()
        for (bus_stop in nearestBusStops) {
            var busStopIcon = resources.getDrawable(R.drawable.marker_poi_flickr)
            val poiMarker = Marker(binding.mapView)
            poiMarker.apply {
                title = bus_stop.busStopName
                val busStopPosition = GeoPoint(bus_stop.busStopLat, bus_stop.busStopLon)
                position = busStopPosition
                icon = busStopIcon
            }
            poiMarkers.add(poiMarker)

            // stop point
            wayPoints.add(GeoPoint(bus_stop.busStopLat, bus_stop.busStopLon))
            roadManager.setMean(OSRMRoadManager.MEAN_BY_CAR)
            val road = roadManager.getRoad(wayPoints)

            if (road.mLength < min) {
                min = road.mLength
                nearestBusStop.clear()
                nearestBusStop.add(bus_stop)
            }

            wayPoints.remove(GeoPoint(bus_stop.busStopLat, bus_stop.busStopLon))
        }
        binding.mapView.overlays.add(poiMarkers)
        binding.mapView.invalidate()
        return nearestBusStop
    }

    // -------------------------------------------------------------------------------------------//
    // Requesting permissions
    private val dialogClickListener =
        DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> { requestPermission() }
                DialogInterface.BUTTON_NEGATIVE -> {  }
                DialogInterface.BUTTON_NEUTRAL -> {  }
            }
        }

    private val negativeAnswerListener = DialogInterface.OnClickListener { _, _ ->
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Функции недоступны, поскольку для них требуются разрешения, " +
                "в которых было отказано")
            .setNeutralButton("Oк", dialogClickListener)
            .show()
    }

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (!isLocationEnabled()) {
                // Open Settings
                Toast.makeText(requireContext(), "Turn on location!", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            } else {
                fusedLocation()
            }
        } else {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Разрешите программе доступ к вашему местоположению для отображения " +
                    "вашего местоположения на карте, а также доступ к вашим файлам для загрузки " +
                    "карты и использования навигации без доступа к сети")
                .setPositiveButton("Да", dialogClickListener)
                .setNegativeButton("Нет", negativeAnswerListener)
                .show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //Toast.makeText(requireContext(), "Permissions granted!", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Функции недоступны, поскольку для них требуются разрешения, " +
                        "в которых было отказано")
                    .setNeutralButton("Oк", dialogClickListener)
                    .show()
            }
        }
    }

    private fun fusedLocation() {
        val file = File(Environment.getExternalStorageDirectory(), "busStopsNames.txt")

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) {
            location = it.result
            mapController.setZoom(18)
            //startPoint = GeoPoint(location.latitude, location.longitude)
            if (startPoint == GeoPoint(0, 0)) {
                startPoint = GeoPoint(37.8897617, 58.3710284)
            }
            /*fromAddress = Address(listOf(), "", "Current location", 1.0,
                startPoint.latitude.toString(),
                "Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright",
                startPoint.longitude.toString())
            binding.searchFromDestination.setQuery(fromAddress.display_name, false)*/
            mapController.setCenter(startPoint)
            mapController.animateTo(startPoint)

            //
            /*val startMarker = Marker(binding.mapView)
            startMarker.position = startPoint
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            binding.mapView.overlays.add(startMarker)
            startMarker.icon = resources.getDrawable(R.drawable.ic_menu_mylocation)
            startMarker.title = "Start point"*/

            val poiMarkers = RadiusMarkerClusterer(requireContext())
            val clusterIcon = resources.getDrawable(R.drawable.marker_cluster)
            poiMarkers.setIcon(clusterIcon.toBitmap(clusterIcon.intrinsicWidth, clusterIcon.intrinsicHeight, null))

            //
            viewModel.apply {
                getAllBusStops()
                allBusStops.observe(viewLifecycleOwner, Observer { busStops ->
                    busStops?.let {
                        for (bus_stop in busStops) {
                            var busStopIcon = resources.getDrawable(R.drawable.marker_poi_default)
                            val poiMarker = Marker(binding.mapView)
                            poiMarker.apply {
                                title = bus_stop.busStopName
                                /*if (bus_stop.busStopName != "") {
                                    busStopIcon = resources.getDrawable(R.drawable.marker_poi_flickr)
                                }*/

                                // TODO("Use view model")
                                /*busStopName = bus_stop.busStopName
                                //getBusNumbers(bus_stop.busStopName)
                                busNumbersString.observe(viewLifecycleOwner, Observer { busNumbersString ->
                                    busNumbersString?.let {
                                        snippet = busNumbersString
                                    }
                                })
                                busNumbers.observe(viewLifecycleOwner, Observer { busNumbers ->
                                    busNumbers?.let {
                                        Log.i("Permission", "${busNumbers}")
                                    }
                                })*/
                                val busNumbers = busStop_BusDao.getAllBusesForThisBusStop(bus_stop.busStopId)
                                var busNumbersString = "Awtobuslar:\n"
                                for (i in busNumbers) {
                                    busNumbersString += "${i.busNumber}\n"
                                }
                                snippet = busNumbersString

                                val busStopPosition = GeoPoint(bus_stop.busStopLat, bus_stop.busStopLon)
                                position = busStopPosition
                                icon = busStopIcon
                                // TODO("Use view model")
                            }

                            poiMarkers.add(poiMarker)
                            //- spinner ---------------------------------------------------------//
                            getAllBuses()
                            allBuses.observe(viewLifecycleOwner, Observer { allBuses ->
                                allBuses?.let {
                                    var busNumbers: ArrayList<Int> = arrayListOf()
                                    for (bus in allBuses) {
                                        busNumbers.add(bus.busNumber)
                                    }
                                    val spinnerAdapter = ArrayAdapter(
                                        requireContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        busNumbers
                                    )
                                    binding.spinner.adapter = spinnerAdapter
                                    binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            p0: AdapterView<*>?,
                                            p1: View?,
                                            p2: Int,
                                            p3: Long
                                        ) {
                                            busId.value = p2
                                        }

                                        override fun onNothingSelected(p0: AdapterView<*>?) {
                                            Toast.makeText(requireContext(),
                                                "Выберите номер автобуса!", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                // Для записи названия остановки в текстовый файл
                                //Log.i("Permission", "$busId")
                                busId.observe(viewLifecycleOwner, Observer { busId ->
                                    busId?.let {
                                        poiMarker.setOnMarkerClickListener { _, _ ->
                                            markerClick(poiMarker, file, bus_stop.busStopId, busId)
                                            return@setOnMarkerClickListener true
                                        }
                                    }
                                })
                            })
                            //-------------------------------------------------------------------//
                        }
                    }
                })
            }

            /*busStopTableRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.children
                    for (i in value) {
                        Log.i("Permission", "Value is: $i")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Permission", "Failed to read value.", error.toException())
                }
            })*/

            binding.apply {
                busStopsFab.setOnClickListener {
                    if (mapView.overlays.contains(poiMarkers))
                        mapView.overlays.remove(poiMarkers)
                    else
                        mapView.overlays.add(poiMarkers)
                }
            }

            //Log.i("SearchApiExample", "POI marker: ${poiMarkers.getItem(4).position}")
            //
            location = it.result
            binding.myLocationFab.setOnClickListener {
                //startPoint = GeoPoint(location.latitude, location.longitude)
                if (startPoint == GeoPoint(0, 0)) {
                    startPoint = GeoPoint(38.0280538, 58.4075067)
                }
                mapController.setZoom(18)
                mapController.setCenter(startPoint)
                mapController.animateTo(startPoint)
            }
        }

        // Показать маршрут автобуса на карте
        /*val roadManager = OSRMRoadManager(requireContext(), "MyOwnUserAgent/1.0")
        val wayPoints = ArrayList<GeoPoint>()
        viewModel.apply {
            for (i in 1..27) {
                val busStops = busStop_BusDao.getAllBusStopsForThisBus(i)
                for (busStop in busStops) {
                    wayPoints.add(GeoPoint(busStop.busStopLat, busStop.busStopLon))
                }
                roadManager.setMean(OSRMRoadManager.MEAN_BY_CAR)
                if (wayPoints.isNotEmpty()) {
                    val road = roadManager.getRoad(wayPoints)
                    val roadOverlay = RoadManager.buildRoadOverlay(road)
                    roadOverlay.outlinePaint.color = Color.rgb(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255))
                    binding.mapView.overlays.add(roadOverlay)
                }
                wayPoints.clear()
            }
        }*/

        binding.mapView.invalidate()
    }

    // -------------------------------------------------------------------------------------------//

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
        Toast.makeText(requireContext(), "Tap on (+${p?.latitude}, ${p?.longitude})", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun longPressHelper(p: GeoPoint?): Boolean {
        val circle = Polygon(binding.mapView)
        circle.apply {
            points = Polygon.pointsAsCircle(p, 10.0)
            fillPaint.color = 0x12121212
            outlinePaint.color = Color.RED
            outlinePaint.strokeWidth = 2F
            infoWindow = BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, binding.mapView)
            title = "Centered on (+${p?.latitude}, ${p?.longitude})"
        }
        binding.mapView.overlays.add(circle)
        vibratePhone()
        binding.mapView.invalidate()
        return true
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }
}