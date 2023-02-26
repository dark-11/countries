package com.example.countries.viewmodel

import androidx.lifecycle.ViewModel
import com.example.countries.dao.CountryDao
import com.example.countries.repository.CountryRepository
import com.example.countries.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val repository: CountryRepository, private val networkHelper: NetworkHelper): ViewModel() {

    val dao by lazy {
        CountryDao(repository,networkHelper)
    }
}