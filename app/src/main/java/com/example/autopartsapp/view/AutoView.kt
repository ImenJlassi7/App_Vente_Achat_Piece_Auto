package com.example.autopartsapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.autopartsapp.R
import com.example.autopartsapp.models.AutoPart
import com.example.autopartsapp.models.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoView(
    onGetUsersClick: () -> Unit,
    onGetPartsClick: () -> Unit,
    onAddPartClick: (AutoPart) -> Unit,
    onDeletePartClick: (AutoPart) -> Unit,
    onModifyPartClick: (AutoPart) -> Unit,
    usersData: List<User>,
    partsData: List<AutoPart>,
    loading: Boolean,
    errorMessage: String,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var isSearchActive by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    var selectedPart by remember { mutableStateOf<AutoPart?>(null) }
    var isShowingParts by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        onGetPartsClick()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onGetUsersClick = {
                        scope.launch { drawerState.close() }
                        isShowingParts = false
                        selectedPart = null // Reset selected part
                        onGetUsersClick()
                    },
                    onGetPartsClick = {
                        scope.launch { drawerState.close() }
                        isShowingParts = true
                        selectedPart = null // Reset selected part
                        onGetPartsClick()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (isSearchActive) {
                            TextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                placeholder = { Text("Rechercher une pièce...") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unfocusedIndicatorColor = Color.Gray,
                                    // Remove the backgroundColor line
                                )
                            )
                        } else {
                            Text("Application de pièces auto")
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (isSearchActive) {
                                    isSearchActive = false
                                    searchText = TextFieldValue("")
                                } else {
                                    scope.launch { drawerState.open() }
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White) // Set icon color to white
                        ) {
                            Icon(
                                imageVector = if (isSearchActive) Icons.Filled.Close else Icons.Filled.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        if (!isSearchActive) {
                            IconButton(onClick = { isSearchActive = true }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFFFFA500), // Orange color
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            }
        ) { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .background(Color(0xFFF2F2F2))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (loading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    } else {
                        if (selectedPart != null) {
                            // Display details of the selected part
                            PartDetailView(selectedPart!!, onBackClick = { selectedPart = null })
                        } else if (isShowingParts) {
                            Text(
                                text = "Pièces Auto",
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            // Part Data Section
                            partsData.forEach { part ->
                                PartCard(part, onModifyPartClick, onDeletePartClick) {
                                    selectedPart = part // Set selected part on click
                                }
                            }
                        } else {
                            /*Text(
                                text = "Utilisateurs",
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )*/
                            usersData.forEach { user ->
                                Text(
                                    text = "${user.name}, ${user.email}",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier
                                        .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                                        .padding(16.dp)
                                )
                            }
                        }

                        if (errorMessage.isNotEmpty()) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PartCard(part: AutoPart, onModifyPartClick: (AutoPart) -> Unit, onDeletePartClick: (AutoPart) -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
            .background(Color(0xFFE0E0E0)), // Gray background for the card
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (part.images.isNotEmpty()) {
                    AsyncImage(
                        model = part.images[0], // Load the first image
                        contentDescription = "${part.name} image",
                        modifier = Modifier
                            .height(100.dp) // Set a specific height for images
                            .fillMaxWidth(),
                        error = painterResource(id = R.drawable.ic_launcher_foreground) // Placeholder for error
                    )
                }
                Text(
                    text = "${part.name}, ${part.price} €",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Row {
                IconButton(onClick = { onModifyPartClick(part) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Modifier pièce")
                }
                IconButton(onClick = { onDeletePartClick(part) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Supprimer pièce")
                }
            }
        }
    }
}

@Composable
fun PartDetailView(part: AutoPart, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
    ) {
        Text(text = part.name, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
        Text(text = part.description, fontSize = 16.sp)
        Text(text = "Price: ${part.price} €", fontSize = 16.sp)
        Text(text = "Brand: ${part.brand}", fontSize = 16.sp)  // Display brand
        Text(text = "Car Model: ${part.carModel}", fontSize = 16.sp)  // Display car model
        Text(text = "Condition: ${part.condition}", fontSize = 16.sp)  // Display condition
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Images:", fontSize = 20.sp)

        // Display all images
        part.images.forEach { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "Image for ${part.name}",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                error = painterResource(id = R.drawable.ic_launcher_foreground) // Placeholder for error
            )
        }

        Button(onClick = onBackClick, modifier = Modifier.padding(top = 16.dp)) {
            Text("Retourner à la liste")
        }
    }
}

@Composable
fun DrawerContent(onGetUsersClick: () -> Unit, onGetPartsClick: () -> Unit) {
    Column {
       /* TextButton(onClick = onGetUsersClick) {
            Text("Utilisateurs")
        }*/
        TextButton(onClick = onGetPartsClick) {
            Text("Pièces Auto")
        }
    }
}
