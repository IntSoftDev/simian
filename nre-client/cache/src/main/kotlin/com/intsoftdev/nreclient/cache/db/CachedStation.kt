package com.intsoftdev.nreclient.cache.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
internal data class CachedStation(
    @PrimaryKey
    var crs: String,
    val name: String,
    val lat: Double,
    val lon: Double
)