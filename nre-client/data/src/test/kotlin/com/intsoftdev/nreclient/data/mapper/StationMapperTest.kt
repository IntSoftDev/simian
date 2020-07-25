package com.intsoftdev.nreclient.data.mapper

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.domain.StationModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class StationMapperTest {

    private lateinit var stationModelMapper: StationModelMapper

    @Before
    fun setUp() {
        stationModelMapper = StationModelMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val stationEntity = makeStationEntity()
        val station = stationModelMapper.mapFromEntity(stationEntity)

        assertStationDataEquality(stationEntity, station)
    }

    @Test
    fun mapToEntityMapsData() {
        val station = makeStation()
        val stationEntity = stationModelMapper.mapToEntity(station)

        assertStationDataEquality(stationEntity, station)
    }

    private fun assertStationDataEquality(stationEntity: StationEntity,
                                          stationModel: StationModel) {
        assertEquals(stationEntity.name, stationModel.stationName)
        assertEquals(stationEntity.crs, stationModel.crsCode)
    }

    private fun makeStationEntity(): StationEntity {
        return StationEntity("WTN", "Whitton", 52.4, 0.03)
    }

    private fun makeStation(): StationModel {
        return StationModel("Whitton", "WTN", 52.4, 0.03)
    }
}