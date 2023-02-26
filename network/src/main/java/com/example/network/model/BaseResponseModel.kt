package com.example.network.model

open class BaseResponseModel<T>(
    var error:Boolean? = null,
    var msg:String? = null,
    var data:T? = null,
)