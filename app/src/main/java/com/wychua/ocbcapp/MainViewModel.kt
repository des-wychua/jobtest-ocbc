package com.wychua.ocbcapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wychua.ocbcapp.data.model.User
import java.util.*


// TODO: change to user id
class MainViewModel(val name: String) : ViewModel() {

    private val _loggedInUser = MutableLiveData<User?>()
    val loggedInUser: LiveData<User?>
        get() = _loggedInUser

    init {
        if (name.isBlank()) {
            _loggedInUser.value = null
        } else {
            // TODO: get user from database
            _loggedInUser.value = User(UUID.randomUUID().toString(), name, 0.0)
        }
    }

    fun logout() {
        _loggedInUser.value = null
    }
}