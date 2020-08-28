package com.intsoftdev.nreclient.cache

import com.intsoftdev.nreclient.cache.db.StationsDatabase
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
import com.intsoftdev.nreclient.data.repository.cache.StationsCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

internal class StationsCacheImpl(
    val stationsDatabase: StationsDatabase
) : StationsCache {

    override fun clearAll(): Completable {
        return Completable.defer {
            stationsDatabase.cachedStationDao().clearStations()
            Completable.complete()
        }
    }

    override fun saveStations(
        version: VersionEntity,
        stations: List<StationEntity>
    ): Completable {
        return Completable.defer {
            stationsDatabase.cachedVersionDao().insertVersion(version)
            stationsDatabase.cachedStationDao().insertStationList(stations)
            Completable.complete()
        }
    }

    override fun getStations(): Observable<List<StationEntity>> {
        return Observable.defer {
            Observable.just(stationsDatabase.cachedStationDao().getStations())
        }
    }

    override fun getVersionDetails(): Observable<VersionEntity> {
        return Observable.defer {
            Observable.just(stationsDatabase.cachedVersionDao().getVersion())
        }
    }

    override fun isCacheEmpty(): Single<Boolean> {
        return Single.defer {
            Single.just(stationsDatabase.cachedStationDao().getStations().isNullOrEmpty())
        }
    }

    override fun getStation(name: String?, crs: String?): Observable<StationEntity> {
        return Observable.defer {
            Observable.just(stationsDatabase.cachedStationDao().getStations().first())
        }
    }
}