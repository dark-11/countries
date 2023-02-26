package com.example.countries.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.viewholder.StateViewHolder
import com.example.network.model.CountryStateResponseModel

/**
 * Adapter class that shows states to view
 */
class StateAdapter(private val stateList: Array<CountryStateResponseModel.States>): RecyclerView.Adapter<StateViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        return StateViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        return holder.bind(stateList[position])
    }
}