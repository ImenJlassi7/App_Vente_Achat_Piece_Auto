package com.example.autopartsapp.models

data class AutoPart(
    val id: String, // Add this line for unique identification
    val name: String,
    val description: String,
    val brand: String,
    val price: Double,
    val carModel: String,
    val condition: String,
    val images: List<String>
)