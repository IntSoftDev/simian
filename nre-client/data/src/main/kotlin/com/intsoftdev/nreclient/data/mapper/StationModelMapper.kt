package com.intsoftdev.nreclient.data.mapper

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.domain.StationModel

/**
 * Map a [StationEntity] to and from a [Station] instance when data is moving between
 * the Data and the Domain layer
 */
internal class StationModelMapper : Mapper<StationEntity, StationModel> {

    /**
     * Map a [StationEntity] instance to a [StationModel] instance
     */
    override fun mapFromEntity(type: StationEntity) =
        StationModel(type.name, type.crs, type.lat, type.lon)

    /**
     * Map a [StationModel] instance to a [StationEntity] instance
     */
    override fun mapToEntity(type: StationModel) =
        StationEntity(
            crs = type.crsCode,
            name = type.stationName,
            lat = type.latitude,
            lon = type.longitude
        )
}