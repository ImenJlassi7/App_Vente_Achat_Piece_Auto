package com.example.autopartsapp.RetrofitClient

import com.example.autopartsapp.ApiService.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Changez l'URL de base si vous utilisez un émulateur Android
// URL de base pour accéder au serveur local depuis un téléphone physique
    val BASE_URL = "http://192.168.1.14:8080"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
