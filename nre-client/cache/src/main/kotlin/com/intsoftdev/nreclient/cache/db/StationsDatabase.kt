package com.intsoftdev.nreclient.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity

@Database(entities = [StationEntity::class, VersionEntity::class], version = 2)
internal abstract class StationsDatabase : RoomDatabase() {
    abstract fun cachedStationDao(): CachedStationDao
    abstract fun cachedVersionDao(): CachedVersionDao
}