package com.intsoftdev.nreclient.cache.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.intsoftdev.nreclient.cache.db.StationsDatabase
import com.intsoftdev.nreclient.data.model.StationEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CachedStationDaoTest {

    private lateinit var stationsDatabase: StationsDatabase

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        stationsDatabase = Room.inMemoryDatabaseBuilder(
                context,
                StationsDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        stationsDatabase.close()
    }

    @Test
    fun insertStationsSavesData() {
        val stationEntity = StationEntity("WTN", "WHITTON", 52.4, 0.03)
        stationsDatabase.cachedStationDao().insertStations(listOf(stationEntity))

        val stations = stationsDatabase.cachedStationDao().getStations()
        assert(stations.isNotEmpty())
    }
}