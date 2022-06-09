package com.example.todoapplication.model.login

import com.example.todoapplication.model.TDUser
import com.example.todoapplication.model.api.BackendResult
import com.example.todoapplication.viewmodel.Result

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class TDLoginRepository(val dataSource: TDLoginService) {

    // in-memory cache of the loggedInUser object
    var user: TDUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        //dataSource.logout()
    }

    fun login(username: String, password: String): Result<TDUser> {
        // handle login
        var result = Result.Success(TDUser(displayName = "", userId = ""))
        val loginRequest = TDLoginRequest(email = username,password = password)
        dataSource.doLogin(loginRequest){response ->
            when(response){
                is BackendResult.Success -> {
                    result = Result.Success(TDUser(displayName = "", userId = ""))
                    setLoggedInUser(result.data)
                }
                else ->{

                }
            }
        }

        return result
    }

    private fun setLoggedInUser(TDUser: TDUser) {
        this.user = TDUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}