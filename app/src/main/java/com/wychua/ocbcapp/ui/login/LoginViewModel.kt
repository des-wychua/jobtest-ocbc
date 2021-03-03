package com.wychua.ocbcapp.ui.login

import androidx.lifecycle.*
import com.wychua.ocbcapp.R
import com.wychua.ocbcapp.data.UserRepository

import com.wychua.ocbcapp.data.model.User
import kotlinx.coroutines.launch
import java.util.*

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(user: User?) {
        if (user != null) {
            _loginResult.value = LoginResult(
                success = user
            )
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    private suspend fun insert(user:User) {
        repository.insert(user)
    }

    fun register (name: String) {
        viewModelScope.launch {
            val user = User(UUID.randomUUID().toString(), name, 0.0)
            insert(user)
            _loginResult.value = LoginResult(
                success = user
            )
        }
    }

    fun getUserByName(name: String): LiveData<List<User>>{
        return repository.getUserByName(name)
    }

    fun loginDataChanged(username: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.length > 2
    }
}