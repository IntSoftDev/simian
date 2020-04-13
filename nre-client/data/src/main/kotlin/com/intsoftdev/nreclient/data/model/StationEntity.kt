package com.intsoftdev.nreclient.data.model

/**
 * Representation for a [StationEntity] fetched from an external layer data source
 */
data class StationEntity(var crs: String,
                         val name: String,
                         val lat: Double,
                         val lon: Double)