package com.intsoftdev.nreclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CachedStation::class), version = 1)
internal abstract class StationsDatabase : RoomDatabase() {
    abstract fun cachedStationDao(): CachedStationDao
}