package com.example.todoapplication.data

import com.example.todoapplication.api.BackendResult
import com.example.todoapplication.api.TDLoginRequest
import com.example.todoapplication.api.TDLoginService
import com.example.todoapplication.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class TDLoginRepository(val dataSource: TDLoginService) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
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

    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        var result = Result.Success(LoggedInUser(displayName = "",userId =""))
        val loginRequest = TDLoginRequest(email = username,password = password)
        dataSource.doLogin(loginRequest){response ->
            when(response){
                is BackendResult.Success -> {
                    result = Result.Success(LoggedInUser(displayName = "",userId =""))
                    setLoggedInUser(result.data)
                }
                else ->{

                }
            }
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}