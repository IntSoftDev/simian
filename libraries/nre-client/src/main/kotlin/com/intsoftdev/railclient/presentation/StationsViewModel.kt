package com.intsoftdev.railclient.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intsoftdev.railclient.api.NREStationsSDK
import com.intsoftdev.railclient.data.StationModel
import com.intsoftdev.railclient.di.DIComponent
import com.intsoftdev.railclient.domain.repository.interactor.GetStationsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.get

class StationsViewModel() : ViewModel(), DIComponent {

    private val nreStationsSDK by lazy { get<NREStationsSDK>() }
    val stationLiveData = MutableLiveData<List<StationModel>>()
    val errorStateLiveData = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()

    override fun onCleared() = compositeDisposable.dispose()

    fun getAllStations() {
        compositeDisposable.clear()
        nreStationsSDK.getAllStations()
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