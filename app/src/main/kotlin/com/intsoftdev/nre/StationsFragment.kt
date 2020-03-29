package com.intsoftdev.nre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.intsoftdev.railclient.data.StationModel
import com.intsoftdev.railclient.presentation.StationsViewModel
import kotlinx.android.synthetic.main.fragment_stations.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class StationsFragment : Fragment() {

    protected val stationsViewModel: StationsViewModel by viewModel()
    private var stationAdapter = StationAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        stationsViewModel.stationLiveData.observe(this,
                Observer<List<StationModel>> { it ->
                    Log.d("get all stations", "size " + it.size)
                    swiperefresh_items.isRefreshing = false
                    handleResponse(it)
                })

        stationsViewModel.errorStateLiveData.observe(this,
                Observer { it ->
                    Log.e("get all stations ", "error " + it)
                    swiperefresh_items.isRefreshing = false
                })

        getItems()
    }

    private fun getItems() {
        stationsViewModel.getAllStations()
    }

    private fun handleResponse(stations: List<StationModel>) {
        stationAdapter.itemsList = stations
    }

    private fun initRecyclerView() {
        stationsRecyclerView.layoutManager = LinearLayoutManager(context)
        stationsRecyclerView.adapter = stationAdapter

        swiperefresh_items.setOnRefreshListener {
            getItems()
        }
    }
}