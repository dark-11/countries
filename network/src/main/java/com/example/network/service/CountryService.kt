package com.example.network.service


import com.example.network.model.CountryStateResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("states")
    suspend fun getCountryStates(): Response<CountryStateResponseModel>
}