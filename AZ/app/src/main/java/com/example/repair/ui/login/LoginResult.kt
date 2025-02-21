package com.example.repair.ui.login

import com.example.repair.data.model.User

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    var success: User? = null,
    val error: Int? = null
)
