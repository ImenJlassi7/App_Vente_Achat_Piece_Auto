package com.example.autopartsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autopartsapp.models.AutoPart
import com.example.autopartsapp.models.User
import com.example.autopartsapp.ui.theme.AutoPartsAppTheme // Assurez-vous d'importer votre thème personnalisé
import com.example.autopartsapp.view.AutoView
import com.example.autopartsapp.viewmodel.AutoPartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoPartsAppTheme {  // Appliquez le thème personnalisé ici
                val viewModel: AutoPartViewModel = viewModel()

                // Récupérer les données initiales des pièces lorsque la composition commence
                LaunchedEffect(Unit) {
                    viewModel.fetchParts { }
                }

                // Interface principale
                AutoView(
                    onGetUsersClick = {
                        viewModel.fetchUsers { }
                    },
                    onGetPartsClick = {
                        viewModel.fetchParts { }
                    },
                    onAddPartClick = { part: AutoPart ->  // Assurez-vous que le type est spécifié
                        // Implémentez la fonctionnalité d'ajout de pièce
                        viewModel.addPart(part) {
                            // Gérez éventuellement le succès ou l'échec ici
                        }
                    },
                    onDeletePartClick = { id: String ->  // Assurez-vous que le type est spécifié
                        // Implémentez la fonctionnalité de suppression de pièce
                        viewModel.deletePart(id) {
                            // Gérez éventuellement le succès ou l'échec ici
                        }
                    },
                    onModifyPartClick = { part: AutoPart ->  // Assurez-vous que le type est spécifié
                        // Implémentez la fonctionnalité de modification de pièce
                        viewModel.modifyPart(part) {
                            // Gérez éventuellement le succès ou l'échec ici
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