package com.intsoftdev.nreclient.data.mapper

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.Version

/**
 * Map a [StationEntity] to and from a [Station] instance when data is moving between
 * the Data and the Domain layer
 */
internal class VersionModelMapper : Mapper<VersionEntity, Version> {

    /**
     * Map a [StationEntity] instance to a [StationModel] instance
     */
    override fun mapFromEntity(type: VersionEntity) =
        Version(type.version, type.lastUpdated)

    /**
     * Map a [StationModel] instance to a [StationEntity] instance
     */
    override fun mapToEntity(type: Version) =
        VersionEntity(version = type.version, lastUpdated = type.lastUpdated)
}