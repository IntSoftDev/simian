package com.intsoftdev.nreclient.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intsoftdev.nreclient.data.model.StationEntity

@Database(entities = arrayOf(StationEntity::class), version = 1)
internal abstract class StationsDatabase : RoomDatabase() {
    abstract fun cachedStationDao(): CachedStationDao
}