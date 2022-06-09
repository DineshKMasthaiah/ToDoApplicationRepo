package com.example.todoapplication.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class TDUser(
        val userId: String,
        val displayName: String,
        val token: String
)