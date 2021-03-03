package com.wychua.ocbcapp

import androidx.lifecycle.*
import com.wychua.ocbcapp.data.UserRepository
import com.wychua.ocbcapp.data.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class PayViewModel(private val repository: UserRepository) : ViewModel() {

    //  get livedata of user list from database
    fun getOrderedUserList(): LiveData<List<User>> {
        return repository.getOrderedUserList()
    }

    private val _paymentComplete = MutableLiveData<Boolean>()
    val paymentComplete: LiveData<Boolean> = _paymentComplete

    private val _payer = MutableLiveData<User>()
    val payer: LiveData<User> = _payer

    private val _payee = MutableLiveData<User>()
    val payee: LiveData<User> = _payee

    fun setPayer(user: User) {
        _payer.value = user
    }

    fun setPayee(user: User) {
        _payee.value = user
    }

    private fun onPaymentComplete() {
        _paymentComplete.value = true
    }

    private suspend fun update(user: User) {
        repository.update(user)
    }

    fun doPaymentTransaction(amount: Double) {
        viewModelScope.launch {
            // payer
            val user1 = User(_payer.value!!.userId, _payer.value!!.name, _payer.value!!.balance - amount)
            // payee
            val user2 = User(_payee.value!!.userId, _payee.value!!.name, _payee.value!!.balance + amount)

            update(user1)
            update(user2)

            onPaymentComplete()
        }
    }


}