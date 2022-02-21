package com.example.posapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel: ViewModel() {
    var totalprice :Int = 0
    val totalpriceLivedata:MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }

    fun getTotalPriceLiveData():LiveData<Int>{
        return totalpriceLivedata
    }
}