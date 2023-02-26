package com.example.network.base

import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

abstract class BaseRepository {

    suspend fun <T> baseRepositoryResponse(apiCall: suspend () -> Response<T>): Resource<T> {
        try {
            Resource.Loading<T>(Resource.STATUS.LOADING)
            val response = apiCall()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    return Resource.Success(responseBody,msg =response.message(), Resource.STATUS.SUCCESS)
                }
            } else {
                return Resource.Error(
                    message = "We are fixing it", status = Resource.STATUS.FAILURE
                )
            }

        } catch (ex: Throwable) {
            if (ex is UnknownHostException ||
                ex is SSLPeerUnverifiedException ||
                ex is SocketTimeoutException ||
                ex is ConnectException
            ) {
                return Resource.Error(
                    message = "Please check your internet connection",
                    status = Resource.STATUS.FAILURE
                )
            }
        }
        return Resource.Error(message = "It failed", status = Resource.STATUS.FAILURE)
    }
}