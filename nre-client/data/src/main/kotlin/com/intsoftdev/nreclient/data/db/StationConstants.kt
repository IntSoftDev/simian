package com.intsoftdev.nreclient.data.db

/**
 * Defines constants for the Stations Table
 */
internal object StationConstants {

    const val TABLE_NAME = "stations"

    const val QUERY_STATIONS = "SELECT * FROM" + " " + TABLE_NAME

    const val DELETE_ALL_STATIONS = "DELETE FROM" + " " + TABLE_NAME

}