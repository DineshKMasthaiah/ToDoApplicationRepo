package com.example.todoapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.todoapplication.model.login.TDLoginRepository

import com.example.todoapplication.R
import com.example.todoapplication.model.TDUser
import com.example.todoapplication.model.api.BackendResult
import com.example.todoapplication.view.login.LoginFormValidationState
import com.example.todoapplication.view.login.TDLoginResult

class LoginViewModel(private val loginRepository: TDLoginRepository) : ViewModel() {

    /**
     *  LoginForm validation state live data  that publishes login form validation result on entering the data into fields
     */
    private val _loginFormValidationState = MutableLiveData<LoginFormValidationState>()
    val loginFormValidationState: LiveData<LoginFormValidationState> = _loginFormValidationState

    /**
     * LoginResult livedata field that publishes login result changes to the subscribed observers(ex:Login Activity)
     */
    private val _loginResult = MutableLiveData<TDLoginResult>()
    val loginResult: LiveData<TDLoginResult> = _loginResult


    /**
     * Activity calls it when the validation of input values for login form is completed.
     */
    fun performLogin(username: String, password: String) {
        // can be launched in a separate asynchronous job
        when(val result:BackendResult<TDUser> = loginRepository.login(username, password)){
             is BackendResult.Success ->{
                 _loginResult.value = TDLoginResult(success = PostLoginViewModel(displayName = (result.data as TDUser).displayName))
             }
             else ->{
                 _loginResult.value = TDLoginResult(error = R.string.login_failed)
             }
         }
    }

    /**
     * Login Activity calls this method when data in the form is edited/added/removed.
     * The Login Activity is called back via an observer registered, post-validation of the value entered in the form
     */
    fun onLoginFormDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginFormValidationState.value = LoginFormValidationState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginFormValidationState.value = LoginFormValidationState(passwordError = R.string.invalid_password)
        } else {
            _loginFormValidationState.value = LoginFormValidationState(isDataValid = true)
        }
    }

    /**
     * Add username validation logic here.. ex: email validation if email is used as username
     */
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    /**
     * Add password validation logic here.. ex: length validation if pwd length is > 5
     */
    private fun isPasswordValid(password: String): Boolean  = password.length > 5
}