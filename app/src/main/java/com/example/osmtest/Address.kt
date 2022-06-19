package com.example.osmtest

data class Address(
    val boundingbox: List<String> = listOf(),
    val `class`: String = "",
    val display_name: String = "Current location",
    val importance: Double = 1.0,
    val lat: String,
    val licence: String = "Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright",
    val lon: String,
    val osm_id: Long = 0,
    val osm_type: String = "",
    val place_id: Int = 0,
    val type: String = ""
)