package com.example.network.base



sealed class Resource<T>(
    val data: T? = null,
    val msg: String? = null,
    val status:STATUS) {
    enum class STATUS {
        SUCCESS,
        FAILURE,
        LOADING
    }

    class Success<T>(body:T, msg: String? = null,status: STATUS):Resource<T>(body,msg,status = STATUS.SUCCESS)

    class Error<T>(body:T?= null,message:String? = null,status:STATUS):Resource<T>(body,status = STATUS.FAILURE)

    class Loading<T>(status:STATUS):Resource<T>(status =STATUS.LOADING)

}