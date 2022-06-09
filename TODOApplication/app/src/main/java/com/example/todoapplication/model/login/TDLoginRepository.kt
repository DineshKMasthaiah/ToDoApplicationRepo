package com.example.todoapplication.model.login

import com.example.todoapplication.model.TDUser
import com.example.todoapplication.model.api.BackendResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class TDLoginRepository(val dataSource: TDLoginService) {

    // in-memory cache
    var user: TDUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }


    fun login(username: String, password: String): BackendResult<TDUser> {
        // handle login
        var result: BackendResult<TDUser> =
            BackendResult.Success(TDUser(displayName = "", userId = "", token = ""))
        val loginRequest = TDLoginRequest(email = username, password = password)
        dataSource.doLogin(loginRequest) { response ->
            when (response) {
                is BackendResult.Success -> {
                    val user = TDUser(
                        displayName = username,
                        userId = username,
                        token = response.data.token
                    )
                    result = BackendResult.Success(user)
                    setLoggedInUser(user)
                }
                else -> {
                    if (response is BackendResult.Error) {
                        result = BackendResult.Error(response.message)
                    }
                }
            }
        }

        return result
    }

    private fun setLoggedInUser(user: TDUser) {
        this.user = user

    }
}