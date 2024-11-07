package com.example.autopartsapp.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val adress: String
)