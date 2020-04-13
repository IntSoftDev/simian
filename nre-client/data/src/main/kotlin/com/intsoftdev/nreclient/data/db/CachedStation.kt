package com.intsoftdev.nreclient.data.db

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