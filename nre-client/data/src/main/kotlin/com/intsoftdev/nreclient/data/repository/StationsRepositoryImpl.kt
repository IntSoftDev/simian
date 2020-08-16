package com.intsoftdev.nreclient.data.repository

import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.mapper.VersionModelMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStore
import com.intsoftdev.nreclient.domain.*
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

internal class StationsRepositoryImpl(
    private val stationsCacheStore: StationsCacheDataStore,
    private val stationsRemoteStore: StationsRemoteDataStore,
    private val stationMapper: StationModelMapper,
    private val versionMapper: VersionModelMapper,
    private val dataUpdateResolver: DataUpdateResolver
) : StationsRepository, StationsCacheDataStore by stationsCacheStore,
    StationsRemoteDataStore by stationsRemoteStore {

    override fun getAllStations(): Observable<StationsResult> {
        Timber.d("getAllStations enter")
        return dataUpdateResolver.getUpdateAction(stationsCacheStore)
            .flatMap { dataUpdateAction ->
                when (dataUpdateAction) {
                    DataUpdateAction.LOCAL -> getCachedStations()
                    DataUpdateAction.REMOTE,
                    DataUpdateAction.REFRESH ->
                        getStationsVersion().flatMap { stationsVersion ->
                            when (dataUpdateAction) {
                                DataUpdateAction.REMOTE -> getStations(stationsVersion.first())
                                DataUpdateAction.REFRESH -> checkStationVersion(stationsVersion.first())
                                else -> throw IllegalStateException("operation invalid")
                            }
                        }
                }
            }
    }

    internal fun checkStationVersion(serverVersion: Version): Observable<StationsResult> {
        Timber.d("checkStationVersion ${serverVersion.version}")
        return getVersionFromCache().flatMap {
            if (serverVersion.version > it.version) {
                getStations(serverVersion)
            } else {
                getCachedStations().doOnComplete {
                    dataUpdateResolver.setLastUpdateTime(System.currentTimeMillis())
                }
            }
        }
    }

    internal fun getStations(version: Version): Observable<StationsResult> {
        Timber.d("getStations ${version.version}")
        return getAllStationsFromServer()
            .flatMap { stations ->
                saveAllStations(version, stations).toSingle { StationsResult(version, stations) }
                    .toObservable()
            }.onErrorReturn {
                // TODO determine cache logic
                throw(it)
            }.doOnComplete {
                dataUpdateResolver.setLastUpdateTime(System.currentTimeMillis())
            }
    }

    override fun saveAllStations(version: Version, stations: List<StationModel>): Completable {
        Timber.d("saveAllStations version: ${version.version} size: ${stations.size}")
        val stationEntities = mutableListOf<StationEntity>()
        stations.map { stationEntities.add(stationMapper.mapToEntity(it)) }
        val versionEntity = versionMapper.mapToEntity(version)
        return saveAllStationsToCache(versionEntity, stationEntities)
    }

    override fun getModelFromCache(
        stationName: String?,
        crsCode: String?
    ) = stationsCacheStore.getFromCache(stationName, crsCode).map {
        stationMapper.mapFromEntity(it)
    }

    internal fun getCachedStations(): Observable<StationsResult> {
        Timber.d("getCachedStations enter")
        return getVersionFromCache().flatMap {
            getAllStationsFromCache().flatMap { stationEntities ->
                Timber.d("cached stations size ${stationEntities.size}")
                Observable.just(
                    StationsResult
                        (
                        stations = stationEntities.map { entity ->
                            stationMapper.mapFromEntity(entity)
                        },
                        version = versionMapper.mapFromEntity(it)
                    )
                )
            }
        }
    }
}