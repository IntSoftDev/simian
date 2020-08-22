package com.intsoftdev.nre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intsoftdev.nreclient.domain.StationModel
import kotlinx.android.synthetic.main.station_row_layout.view.*
import kotlin.properties.Delegates

class StationAdapter : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    private val stations = mutableListOf<StationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.station_row_layout, parent, false)
        return StationViewHolder(view)
    }

    override fun getItemCount() = stations.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stations[position])
    }

    fun updateData(items: List<StationModel>) {
        stations.clear()
        stations.addAll(items)
        notifyDataSetChanged()
    }

    class StationViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(stationModel: StationModel) {
            view.station_name.text = stationModel.stationName
        }
    }

}