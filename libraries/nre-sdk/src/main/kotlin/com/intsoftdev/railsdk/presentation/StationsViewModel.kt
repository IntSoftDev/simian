package com.intsoftdev.railsdk.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intsoftdev.railsdk.data.StationModel
import com.intsoftdev.railsdk.di.DIComponent
import com.intsoftdev.railsdk.domain.repository.interactor.GetStationsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.get

class StationsViewModel() : ViewModel(), DIComponent {

    private val getStationsUseCase by lazy { get<GetStationsUseCase>() }
    val stationLiveData = MutableLiveData<List<StationModel>>()
    val errorStateLiveData = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()

    override fun onCleared() = compositeDisposable.dispose()

    fun getAllStations() {
        compositeDisposable.clear()
        getStationsUseCase.getAllStations()
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