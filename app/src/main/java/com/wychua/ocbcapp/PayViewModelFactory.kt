package com.wychua.ocbcapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wychua.ocbcapp.data.UserRepository

class PayViewModelFactory(val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}