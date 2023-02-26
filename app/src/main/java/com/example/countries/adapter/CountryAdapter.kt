package com.example.countries.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.viewholder.CountryViewHolder
import com.example.network.model.CountryStateResponseModel

/**
 * Adapter class that shows the country data to view
 */
class CountryAdapter(private val countryList:List<CountryStateResponseModel.Data>, private val countryClick: (List<CountryStateResponseModel.States>) -> Unit): RecyclerView.Adapter<CountryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        return holder.bind(countryList[position], clickListener = countryClick)
    }
}