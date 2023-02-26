package com.example.countries.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.ItemLayoutBinding
import com.example.countries.visible
import com.example.network.model.CountryStateResponseModel

class StateViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun inflate(parent: ViewGroup): StateViewHolder {
            return StateViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }
    fun bind(item: CountryStateResponseModel.States?) {
        item?.let {
            if (!item.name.isNullOrEmpty()) {
                binding.title.visible()
                binding.title.text = "${item.name}"
            }
            if (!item.stateCode.isNullOrEmpty()) {
                binding.subTitle.visible()
                binding.subTitle.text = "${item.stateCode}"
            }
        }
    }
}