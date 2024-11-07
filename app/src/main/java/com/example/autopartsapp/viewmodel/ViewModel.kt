package com.example.autopartsapp.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autopartsapp.RetrofitClient.RetrofitClient
import com.example.autopartsapp.models.AutoPart
import com.example.autopartsapp.models.User
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.Part

class AutoPartViewModel : ViewModel() {
    var usersData by mutableStateOf<List<User>>(emptyList())
    var partsData by mutableStateOf<List<AutoPart>>(emptyList())
    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun fetchUsers(onResult: (List<User>) -> Unit) {
        loading = true
        RetrofitClient.instance.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                loading = false
                if (response.isSuccessful) {
                    usersData = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error fetching users: ${response.message()}"
                }
                onResult(usersData)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                loading = false
                errorMessage = "Error: ${t.localizedMessage}"
                onResult(emptyList())
            }
        })
    }

    fun getParts() {
        loading = true
        RetrofitClient.instance.getAllParts().enqueue(object : Callback<List<AutoPart>> {
            override fun onResponse(call: Call<List<AutoPart>>, response: Response<List<AutoPart>>) {
                loading = false
                if (response.isSuccessful) {
                    partsData = response.body() ?: emptyList()
                } else {
                    errorMessage = "Erreur lors de la récupération des pièces"
                }
            }

            override fun onFailure(call: Call<List<AutoPart>>, t: Throwable) {
                loading = false
                errorMessage = "Erreur: ${t.localizedMessage}"
            }
        })
    }

    fun addPart(part: AutoPart, onComplete: () -> Unit) {
        viewModelScope.launch {
            // Implement your API call to add the part
            onComplete()
        }
    }

    // Function to delete part
    fun deletePart(id: String, onComplete: (Boolean) -> Unit) {
        loading = true
        println("Attempting to delete part with ID: $id") // Ajoutez ce log
        RetrofitClient.instance.deletePart(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                loading = false
                if (response.isSuccessful) {
                    onComplete(true) // Succès
                } else {
                    errorMessage = "Failed to delete part: ${response.message()}"
                    onComplete(false) // Échec
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading = false
                errorMessage = "Error: ${t.localizedMessage}"
                onComplete(false) // Échec
            }
        })
    }



    fun modifyPart(part: AutoPart, onComplete: () -> Unit) {
        viewModelScope.launch {
            // Implement your API call to modify the part
            onComplete()
        }
    }
}
