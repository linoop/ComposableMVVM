package com.linoop.composablemvvm.models

data class User(
    val email: String,
    val first_name: String,
    val id: Int,
    val is_active: Boolean,
    val is_staff: Boolean,
    val is_superuser: Boolean,
    val last_login: Any,
    val last_name: String,
    val password: String,
    val username: String
)
