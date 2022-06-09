package com.example.todoapplication.view.login

import com.example.todoapplication.viewmodel.PostLoginViewModel

/**
 * Authentication result : success (user details) or error message.
 */
data class TDLoginResult(
        val success: PostLoginViewModel? = null,
        val error: Int? = null
)