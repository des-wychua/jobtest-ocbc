package com.wychua.ocbcapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wychua.ocbcapp.data.UserRepository

class TopUpViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}