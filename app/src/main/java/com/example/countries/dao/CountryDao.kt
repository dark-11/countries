package com.example.countries.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.countries.repository.CountryRepository
import com.example.countries.utils.NetworkHelper
import com.example.network.base.Resource
import com.example.network.model.CountryStateResponseModel
import kotlinx.coroutines.*

/**
 *  CountryDao to expose data to view through viewmodel
 *
 */
class CountryDao (private val repository: CountryRepository, private val networkHelper: NetworkHelper) {
    private val responseOfCountryStates = MutableLiveData<Resource<CountryStateResponseModel>>()
    private val countryStateResponse: LiveData<Resource<CountryStateResponseModel>> = responseOfCountryStates

    init { if(networkHelper.isNetworkConnected()) fetchData() }

    fun retryFetchData() {
        fetchData()
    }
     private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
                repository.getData().collect {
                    responseOfCountryStates.postValue(it)
            }
        }
    }



    val success = Transformations.map(countryStateResponse) {
        if (it.status == Resource.STATUS.SUCCESS) {
             it.data
        } else {
            null
        }
        it
    }

    val failure = Transformations.map(countryStateResponse) {
        if (it.data?.data!=null && it.status == Resource.STATUS.FAILURE) {
            Pair("We are fixing it",it.msg)
        } else {
            null
        }
    }
    val loading = Transformations.map(countryStateResponse) {
        it?.status == Resource.STATUS.LOADING
    }



}