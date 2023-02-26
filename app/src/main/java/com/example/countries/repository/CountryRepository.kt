package com.example.countries.repository

import com.example.countries.datasource.CountryDataSource
import com.example.network.base.BaseRepository
import com.example.network.base.Resource
import com.example.network.model.CountryStateResponseModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class CountryRepository @Inject constructor(private val countryDataSource: CountryDataSource):BaseRepository() {

    suspend fun getData(): Flow<Resource<CountryStateResponseModel>> {
        return flow<Resource<CountryStateResponseModel>> { emit(baseRepositoryResponse { countryDataSource.getData() }) }.flowOn(Dispatchers.IO)
    }

}