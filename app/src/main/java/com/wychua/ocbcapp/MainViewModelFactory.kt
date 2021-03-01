package com.wychua.ocbcapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// TODO: change to user id
class MainViewModelFactory(private val name: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(name) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}