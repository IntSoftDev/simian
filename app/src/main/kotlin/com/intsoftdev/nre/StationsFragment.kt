package com.intsoftdev.nre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsResult
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
        stationsViewModel.result.observe(this,
                Observer<StationsResult> { it ->
                    Log.d("get all stations", "size " + it.stations.size)
                    swiperefresh_items.isRefreshing = false
                    handleResponse(it.stations)
                })

        stationsViewModel.errorStateLiveData.observe(this,
                Observer { it ->
                    Log.e("get all stations ", "error " + it)
                    swiperefresh_items.isRefreshing = false

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage(it.message)
                    val dialog: AlertDialog = builder.create()
                    // Display the alert dialog on app interface
                    dialog.show()
                })

        stationsViewModel.loadingLiveData.observe(this,
                Observer {it ->
                    stationsLoading.visibility = if (it) View.VISIBLE else View.GONE
                })

        getItems()
    }

    private fun getItems() {
        stationsViewModel.getAllStations()
    }

    private fun handleResponse(stations: List<StationModel>) {
        stationAdapter.updateData(stations)
    }

    private fun initRecyclerView() {
        stationsRecyclerView.layoutManager = LinearLayoutManager(context)
        stationsRecyclerView.adapter = stationAdapter

        swiperefresh_items.setOnRefreshListener {
            getItems()
        }
    }
}