package com.example.countries.datasource


import com.example.network.service.CountryService
import javax.inject.Inject

class CountryDataSource @Inject constructor (private val countryService: CountryService) {

    suspend fun getData() = countryService.getCountryStates()
}