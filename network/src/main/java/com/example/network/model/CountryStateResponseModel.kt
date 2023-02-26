package com.example.network.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class CountryStateResponseModel : BaseResponseModel<List<CountryStateResponseModel.Data>>(),
    Parcelable {
    @Parcelize
    class States(
        var name: String,
        var stateCode: String
    ) : Parcelable

    @Parcelize
    class Data(
        var name: String,
        var iso3: String,
        var iso2: String,
        var states: List<States>? = null
    ) : Parcelable {
        @JvmName("iso3String")
        fun getIso3(): String {
            return iso3
        }

    }
}