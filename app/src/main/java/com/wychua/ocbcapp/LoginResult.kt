package com.wychua.ocbcapp

import com.wychua.ocbcapp.data.model.User

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: User? = null,
    val error: Int? = null
)