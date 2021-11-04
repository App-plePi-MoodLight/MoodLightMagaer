package com.example.moodlightmanger.model

data class AuthModel(
    val created_date: String,
    val email: String,
    val id: String,
    val is_admin: Boolean,
    val nickname: String,
    val password: String
)