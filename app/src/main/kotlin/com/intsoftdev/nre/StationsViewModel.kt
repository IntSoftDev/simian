package com.intsoftdev.nre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intsoftdev.nreclient.domain.ApiError
import com.intsoftdev.nreclient.domain.ReturnState
import com.intsoftdev.nreclient.domain.StationsResult
import com.intsoftdev.railclient.api.StationsClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class StationsViewModel(private val stationsClient: StationsClient) : ViewModel() {

    val result = MutableLiveData<StationsResult>()
    val errorStateLiveData = MutableLiveData<ApiError>()
    val loadingLiveData = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()

    override fun onCleared() = compositeDisposable.dispose()

    fun getAllStations() {
        compositeDisposable.clear()
        stationsClient.getAllStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { returnState ->
                when (returnState) {
                    is ReturnState.Success -> {
                        result.value = returnState.data
                        loadingLiveData.value = false
                    }
                    is ReturnState.Error -> {
                        Timber.e("error ${returnState.apiError}")
                        errorStateLiveData.value = returnState.apiError
                        loadingLiveData.value = false
                    }
                    is ReturnState.Loading -> {
                        Timber.d("loading")
                        loadingLiveData.value = false
                    }
                }
            }.also { compositeDisposable.add(it) }
    }
}