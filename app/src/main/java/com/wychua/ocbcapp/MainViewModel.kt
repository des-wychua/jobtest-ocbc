package com.wychua.ocbcapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wychua.ocbcapp.data.UserRepository
import com.wychua.ocbcapp.data.model.User
import java.util.*


class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loggedInUser = MutableLiveData<User?>()
    val loggedInUser: LiveData<User?>
        get() = _loggedInUser

    fun onLoggedInUser(user: User?) {
        _loggedInUser.value = user
    }

    fun getLoggedInUser(userId: String): LiveData<List<User>> {
        return repository.getUserByUserId(userId)
    }

    fun logout() {
        _loggedInUser.value = null
    }

    fun refreshUserData() {

    }
}