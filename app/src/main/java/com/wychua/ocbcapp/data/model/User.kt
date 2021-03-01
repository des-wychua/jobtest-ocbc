package com.wychua.ocbcapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val userId: String,
    val name: String,
    val balance: Double
)