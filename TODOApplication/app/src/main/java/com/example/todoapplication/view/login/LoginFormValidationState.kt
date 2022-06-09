package com.example.todoapplication.view.login

/**
 * Data validation state of the login form.
 */
data class LoginFormValidationState(val usernameError: Int? = null,
                                    val passwordError: Int? = null,
                                    val isDataValid: Boolean = false)