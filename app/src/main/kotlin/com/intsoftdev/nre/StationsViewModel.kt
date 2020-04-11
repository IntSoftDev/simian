package com.intsoftdev.nre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.railclient.api.StationsClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StationsViewModel(private val stationsClient: StationsClient) : ViewModel() {

    val stationLiveData = MutableLiveData<List<StationModel>>()
    val errorStateLiveData = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()

    override fun onCleared() = compositeDisposable.dispose()

    fun getAllStations() {
        compositeDisposable.clear()
        stationsClient.getAllStations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ stations ->
                    stationLiveData.postValue(stations)
                    errorStateLiveData.postValue(false)
                }) { throwable ->
                    errorStateLiveData.postValue(true)
                }.also { compositeDisposable.add(it) }
    }
}