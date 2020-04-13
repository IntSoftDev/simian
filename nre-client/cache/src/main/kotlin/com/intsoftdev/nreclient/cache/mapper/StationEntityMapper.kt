package com.intsoftdev.nreclient.cache.mapper

import com.intsoftdev.nreclient.cache.db.CachedStation
import com.intsoftdev.nreclient.data.model.StationEntity

/**
 * Map a [CachedStation] instance to and from a [StationEntity] instance when data is moving between
 * this later and the Data layer
 */
internal class StationEntityMapper :
        EntityMapper<CachedStation, StationEntity> {

    /**
     * Map a [StationEntity] instance to a [CachedStation] instance
     */
    override fun mapToCached(type: StationEntity)
            = CachedStation(type.crs, type.name, type.lat, type.lon)

    /**
     * Map a [CachedStation] instance to a [StationEntity] instance
     */
    override fun mapFromCached(type: CachedStation)
            = StationEntity(type.crs, type.name, type.lat, type.lon)

}