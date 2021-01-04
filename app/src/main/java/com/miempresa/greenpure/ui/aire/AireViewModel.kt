package com.miempresa.greenpure.ui.aire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AireViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is air Fragment"
    }
    val text: LiveData<String> = _text
}