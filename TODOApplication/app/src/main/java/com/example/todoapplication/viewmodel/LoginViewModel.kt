package com.example.todoapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.todoapplication.model.login.TDLoginRepository

import com.example.todoapplication.R
import com.example.todoapplication.view.login.LoginFormState
import com.example.todoapplication.view.login.LoginState

class LoginViewModel(private val loginRepository: TDLoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

//TODO: navigate to next screen if login is successful
    private val _loginResult = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginResult

    //TODO: Actual login method that activity calls on pressing login button
    //calls login repo in turn and returns the LoginUserObject
    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value = LoginState(success = PostLoginViewModel(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginState(error = R.string.login_failed)
        }
    }

    //Activity calls when data in the form is edited/added/removed
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}