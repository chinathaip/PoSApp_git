package com.example.posapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PosSettingViewModel:ViewModel() {
    val MusicStatusLive: MutableLiveData<String> = MutableLiveData(PosSettings.statusText)

    fun getMusicStatusLiveText():LiveData<String>{
        return MusicStatusLive
    }
}