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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun fetchParts(onResult: (List<AutoPart>) -> Unit) {
        loading = true
        RetrofitClient.instance.getAllParts().enqueue(object : Callback<List<AutoPart>> {
            override fun onResponse(call: Call<List<AutoPart>>, response: Response<List<AutoPart>>) {
                loading = false
                if (response.isSuccessful) {
                    partsData = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error fetching parts: ${response.message()}"
                }
                onResult(partsData)
            }

            override fun onFailure(call: Call<List<AutoPart>>, t: Throwable) {
                loading = false
                errorMessage = "Error: ${t.localizedMessage}"
                onResult(emptyList())
            }
        })
    }
    fun addPart(part: AutoPart, onComplete: () -> Unit) {
        RetrofitClient.instance.addAutoPart(part).enqueue(object : Callback<AutoPart> {
            override fun onResponse(call: Call<AutoPart>, response: Response<AutoPart>) {
                if (response.isSuccessful) {
                    // Update the parts data and reset error message
                    partsData = partsData + (response.body() ?: part) // Add the new part to the list
                    errorMessage = "" // Clear any previous error
                } else {
                    errorMessage = "Error adding part: ${response.message()}"
                }
                onComplete()
            }

            override fun onFailure(call: Call<AutoPart>, t: Throwable) {
                errorMessage = "Error: ${t.localizedMessage}"
                onComplete()
            }
        })
    }

    fun deletePart(id: String, onComplete: () -> Unit) {
        RetrofitClient.instance.deleteAutoPart(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Remove the deleted part from the list
                    partsData = partsData.filter { it.id != id }
                    errorMessage = "" // Clear any previous error
                } else {
                    errorMessage = "Error deleting part: ${response.message()}"
                }
                onComplete()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                errorMessage = "Error: ${t.localizedMessage}"
                onComplete()
            }
        })
    }

    fun modifyPart(part: AutoPart, onComplete: () -> Unit) {
        RetrofitClient.instance.updateAutoPart(part.id, part).enqueue(object : Callback<AutoPart> {
            override fun onResponse(call: Call<AutoPart>, response: Response<AutoPart>) {
                if (response.isSuccessful) {
                    // Update the parts data with the modified part
                    partsData =
                        partsData.map { if (it.id == part.id) response.body() ?: it else it }
                    errorMessage = "" // Clear any previous error
                } else {
                    errorMessage = "Error modifying part: ${response.message()}"
                }
                onComplete()
            }

            override fun onFailure(call: Call<AutoPart>, t: Throwable) {
                errorMessage = "Error: ${t.localizedMessage}"
                onComplete()
            }
        })
    }}
