package com.intsoftdev.nreclient.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intsoftdev.nreclient.data.model.StationEntity

@Dao
internal abstract class CachedStationDao {

    @Query(StationConstants.QUERY_STATIONS)
    abstract fun getStations(): List<StationEntity>

    @Query(StationConstants.DELETE_ALL_STATIONS)
    abstract fun clearStations()

    @Query(StationConstants.QUERY_WITH_CRS)
    abstract fun queryWithCrs(crs: String): StationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertStations(stationEntities: List<StationEntity>)
}