package com.intsoftdev.nreclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal abstract class CachedStationDao {

    @Query(StationConstants.QUERY_STATIONS)
    abstract fun getStations(): List<CachedStation>

    @Query(StationConstants.DELETE_ALL_STATIONS)
    abstract fun clearStations()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertStation(cachedStation: CachedStation)
}