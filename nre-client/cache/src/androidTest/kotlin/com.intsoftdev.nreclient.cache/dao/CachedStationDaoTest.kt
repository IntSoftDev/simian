package com.intsoftdev.nreclient.cache.dao

import android.content.Context
import androidx.room.Room
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.intsoftdev.nreclient.cache.db.CachedStation
import com.intsoftdev.nreclient.cache.db.StationsDatabase

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
        val cachedStation = makeCachedStation()
        stationsDatabase.cachedStationDao().insertStation(cachedStation)

        val stations = stationsDatabase.cachedStationDao().getStations()
        assert(stations.isNotEmpty())
    }

    private fun makeCachedStation(): CachedStation {
        return CachedStation("WTN", "Whitton", 52.4, 0.03)
    }

   /* @Test
    fun getBufferoosRetrievesData() {
        val cachedBufferoos = BufferooFactory.makeCachedBufferooList(5)

        cachedBufferoos.forEach {
            bufferoosDatabase.cachedBufferooDao().insertBufferoo(it) }

        val retrievedBufferoos = bufferoosDatabase.cachedBufferooDao().getBufferoos()
        assert(retrievedBufferoos == cachedBufferoos.sortedWith(compareBy({ it.id }, { it.id })))
    }*/

}