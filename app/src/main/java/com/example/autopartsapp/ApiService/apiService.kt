package com.example.autopartsapp.ApiService

import com.example.autopartsapp.models.AutoPart
import com.example.autopartsapp.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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
    fun addPart(@Body autoPart: AutoPart): Call<AutoPart>

    // Supprimer une pièce auto
    @DELETE("/api/parts/{id}")  // Added leading slash to ensure proper path
    fun deletePart(@Path("id") id: String): Call<Unit>  // Use Call<Unit> instead of Call<Response<Void>>

    // Mettre à jour une pièce auto
    @PUT("/api/parts/{id}")
    fun updatePart(@Path("id") id: String, @Body part: AutoPart): Call<AutoPart>
}
