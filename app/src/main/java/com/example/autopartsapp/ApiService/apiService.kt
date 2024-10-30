// File: ApiService.kt
package com.example.autopartsapp.ApiService

import com.example.autopartsapp.models.AutoPart
import com.example.autopartsapp.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Récupérer tous les utilisateurs
    @GET("/api/users")
    fun getAllUsers(): Call<List<User>>

    // Ajouter un utilisateur
    @POST("/api/users")
    fun addUser(@Body user: User): Call<User>

    // Récupérer toutes les pièces auto
    @GET("/api/parts")
    fun getAllParts(): Call<List<AutoPart>>

    // Ajouter une pièce auto
    @POST("/api/parts")
    fun addAutoPart(@Body autoPart: AutoPart): Call<AutoPart>
}
