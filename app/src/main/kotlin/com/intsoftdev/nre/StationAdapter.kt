package com.intsoftdev.nre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intsoftdev.railclient.data.StationModel
import kotlinx.android.synthetic.main.station_row_layout.view.*
import kotlin.properties.Delegates

class StationAdapter : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    var itemsList: List<StationModel> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.station_row_layout, parent, false)
        return StationViewHolder(view)
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    class StationViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(stationModel: StationModel) {
            view.station_name.text = stationModel.stationName
        }
    }

}