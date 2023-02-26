package com.example.countries.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.ItemLayoutBinding
import com.example.countries.visible
import com.example.network.model.CountryStateResponseModel

/**
 *  ViewHolder of country to show the data
 */
class CountryViewHolder(private val binding:ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun inflate(parent:ViewGroup): CountryViewHolder {
            return CountryViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }
    fun bind(item:CountryStateResponseModel.Data, clickListener:(List<CountryStateResponseModel.States>)-> Unit) {
        item?.let {
            if (!item.name.isNullOrEmpty()) {
                binding.title.visible()
                binding.title.text = "Country: ${item.name}"
            }
            if (!item.iso3.isNullOrEmpty()) {
                binding.subTitle.visible()
                binding.subTitle.text = "ISO3: ${item.iso3}"
            }
        }
        binding.container.setOnClickListener { clickListener.invoke(item.states!!) }
    }

}