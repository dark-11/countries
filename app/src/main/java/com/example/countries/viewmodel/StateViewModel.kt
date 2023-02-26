package com.example.countries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  ViewModel of StateFragment
 */
@HiltViewModel
class StateViewModel @Inject constructor(): ViewModel() {
    val responseOfStates = MutableLiveData<Boolean>()
    private val stateResponse: LiveData<Boolean> = responseOfStates

    val success = Transformations.map(stateResponse) {
        it
    }

    val failure = Transformations.map(stateResponse) {
       it
    }
    val loading = Transformations.map(stateResponse) {
       it
    }

}