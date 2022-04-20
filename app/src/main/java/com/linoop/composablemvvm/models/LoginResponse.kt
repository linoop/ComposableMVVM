package com.linoop.composablemvvm.models

data class LoginResponse(
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)