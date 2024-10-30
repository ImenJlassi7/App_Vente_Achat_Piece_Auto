package com.example.autopartsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autopartsapp.models.AutoPart
import com.example.autopartsapp.models.User
import com.example.autopartsapp.ui.theme.AutoPartsAppTheme // Ensure you import your custom theme
import com.example.autopartsapp.view.AutoView
import com.example.autopartsapp.viewmodel.AutoPartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoPartsAppTheme {  // Apply the custom theme here
                val viewModel: AutoPartViewModel = viewModel()

                // Fetch initial parts data when the composition starts
                LaunchedEffect(Unit) {
                    viewModel.fetchParts { }
                }

                // Main UI
                AutoView(
                    onGetUsersClick = {
                        viewModel.fetchUsers { }
                    },
                    onGetPartsClick = {
                        viewModel.fetchParts { }
                    },
                    onAddPartClick = { part ->
                        // Implement add part functionality
                        viewModel.addPart(part) {
                            // Optionally handle success or failure here
                        }
                    },
                    onDeletePartClick = { id ->
                        // Implement delete part functionality
                        viewModel.deletePart(id) {
                            // Optionally handle success or failure here
                        }
                    },
                    onModifyPartClick = { part ->
                        // Implement modify part functionality
                        viewModel.modifyPart(part) {
                            // Optionally handle success or failure here
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
