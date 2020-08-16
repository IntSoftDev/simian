package com.intsoftdev.nreclient.cache.db

import androidx.room.*
import com.intsoftdev.nreclient.data.model.StationEntity

@Dao
internal interface CachedStationDao {

    @Query("SELECT * from stationsTable")
    fun getStations(): List<StationEntity>

    @Query("DELETE from stationsTable")
    fun clearStations()

    @Query("SELECT * from stationsTable WHERE crs_code == :crs")
    fun queryWithCrs(crs: String): StationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStations(stationEntities: List<StationEntity>)

    @Transaction
    fun insertStationList(list: List<StationEntity>) {
        clearStations()
        insertStations(list)
    }
}