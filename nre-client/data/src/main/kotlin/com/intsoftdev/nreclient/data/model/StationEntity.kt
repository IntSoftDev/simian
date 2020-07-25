package com.intsoftdev.nreclient.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representation for a [StationEntity] fetched from an external layer data source
 */
@Entity(tableName = "stations")
data class StationEntity(
        @PrimaryKey
        @ColumnInfo(name = "crs_code") var crs: String,
        var name: String,
        var lat: Double,
        var lon: Double
)