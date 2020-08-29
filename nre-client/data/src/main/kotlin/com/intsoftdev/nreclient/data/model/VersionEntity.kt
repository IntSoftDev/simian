package com.intsoftdev.nreclient.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representation for a [VersionEntity] fetched from an external layer data source
 */
@Entity(tableName = "versionTable")
data class VersionEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Long = 0,
    @ColumnInfo(name = "version") var version: Double,
    @ColumnInfo(name = "lastUpdated") val lastUpdated: Long

)