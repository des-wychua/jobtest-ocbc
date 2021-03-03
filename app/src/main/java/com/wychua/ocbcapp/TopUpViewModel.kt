package com.wychua.ocbcapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wychua.ocbcapp.data.UserRepository
import com.wychua.ocbcapp.data.model.User
import kotlinx.coroutines.launch

class TopUpViewModel(private val repository: UserRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser

    private val _topupComplete = MutableLiveData<Boolean>()
    val topupComplete : LiveData<Boolean> = _topupComplete

    fun getUserByUserId(userId: String): LiveData<List<User>> {
        return repository.getUserByUserId(userId)
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    private fun onTopUpComplete() {
        _topupComplete.value = true
    }

    private suspend fun update(user: User) {
        repository.update(user)
    }

    fun doTopUpTransaction(amount: Double) {
        viewModelScope.launch {
            // payer
            val user = User(_currentUser.value!!.userId, _currentUser.value!!.name, _currentUser.value!!.balance + amount)

            update(user)

            onTopUpComplete()
        }
    }
}