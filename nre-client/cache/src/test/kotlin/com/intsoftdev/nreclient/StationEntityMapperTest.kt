package com.intsoftdev.nreclient

import com.intsoftdev.nreclient.cache.db.CachedStation
import com.intsoftdev.nreclient.cache.di.cacheModule
import com.intsoftdev.nreclient.cache.mapper.StationEntityMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class StationEntityMapperTest : KoinTest {

    private lateinit var stationEntityMapper: StationEntityMapper

    @get:Rule
    val koinRule = KoinTestRule.create {
        modules(cacheModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun setUp() {
        stationEntityMapper = StationEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val stationEntity = makeStationEntity()
        val cachedStation = stationEntityMapper.mapToCached(stationEntity)
        assertStationDataEquality(stationEntity, cachedStation)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedStation = makeCachedStation()
        val stationEntity = stationEntityMapper.mapFromCached(cachedStation)
        assertStationDataEquality(stationEntity, cachedStation)
    }

    private fun assertStationDataEquality(stationEntity: StationEntity,
                                          cachedStation: CachedStation) {
        assertEquals(stationEntity.name, cachedStation.name)
        assertEquals(stationEntity.crs, cachedStation.crs)
    }

    private fun makeStationEntity(): StationEntity {
        return StationEntity("WTN", "Whitton", 52.4, 0.03)
    }

    private fun makeCachedStation(): CachedStation {
        return CachedStation("WTN", "Whitton", 52.4, 0.03)
    }
}