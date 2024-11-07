package com.example.autopartsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autopartsapp.models.AutoPart
import com.example.autopartsapp.models.User
import com.example.autopartsapp.ui.theme.AutoPartsAppTheme // Ensure you import your custom theme
import com.example.autopartsapp.view.AutoView
import com.example.autopartsapp.viewmodel.AutoPartViewModel
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoPartsAppTheme {
                val viewModel: AutoPartViewModel = viewModel()

                // Fetch initial parts data when the composition starts
                LaunchedEffect(Unit) {
                    viewModel.getParts()
                }

                // Main UI
                AutoView(
                    onGetUsersClick = {
                        viewModel.fetchUsers { }
                    },
                    onGetPartsClick = {
                        viewModel.getParts()
                    },
                    onAddPartClick = { part ->
                        viewModel.addPart(part) {
                            // Handle success or failure
                        }
                    },
                    onDeletePartClick = { part ->
                        viewModel.deletePart(part.id) { success ->
                            if (success) {
                                Toast.makeText(this@MainActivity, "Part deleted successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity, "Failed to delete part", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    ,
                    onModifyPartClick = { part ->
                        viewModel.modifyPart(part) {
                            // Handle success or failure
                        }
                    },
                    usersData = viewModel.usersData,
                    partsData = viewModel.partsData,
                    loading = viewModel.loading,
                    errorMessage = viewModel.errorMessage
                )
            }
        }
    }
}


