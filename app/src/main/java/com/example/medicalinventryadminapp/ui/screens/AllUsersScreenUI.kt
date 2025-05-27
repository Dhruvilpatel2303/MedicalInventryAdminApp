package com.example.medicalinventryadminapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicalinventryadminapp.navigation.routes
import com.example.medicalinventryadminapp.network.response.UsersResponse.GetAllusers.GetAllUsersResponseItem
import com.example.medicalinventryadminapp.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllUsersScreenUI(viewModel: MainViewModel, navController: NavController) {
    val getalluserstate = viewModel.alluserstate.collectAsState().value
    val filterOptions = listOf("All", "Approved", "Not Approved")
    var selectedFilter by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        viewModel.getAllusers()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(10,90,10,10),
                title = {
                    Text(
                        text = "Medical Inventory Admin",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2C3E50)),
                actions = {
                    IconButton(onClick = { viewModel.getAllusers() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.White)
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 42.dp)
                .background(Color.Black.copy(alpha = 0.02f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                filterOptions.forEach { option ->
                    Button(
                        onClick = { selectedFilter = option },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedFilter == option) Color(0xFF4CAF50) else Color(0xFF3A3A3A)
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(option, color = Color.White)
                    }
                }
            }

            when {
                getalluserstate.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF4CAF50))
                    }
                }

                getalluserstate.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${getalluserstate.error}", color = Color.Red)
                    }
                }

                getalluserstate.success != null -> {
                    val filteredUsers = when (selectedFilter) {
                        "Approved" -> getalluserstate.success.filter { it.isApproved == 1 }
                        "Not Approved" -> getalluserstate.success.filter { it.isApproved == 0 }
                        else -> getalluserstate.success
                    }

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredUsers) { user ->
                            UserCardEnhanced(
                                user = user,
                                onApproveClick = {
                                    viewModel.approveUser(user.user_id, 1)
                                    viewModel.getAllusers()
                                },
                                onBlockClick = {
                                    viewModel.approveUser(user.user_id, 0)
                                    viewModel.getAllusers()
                                },
                                onDeleteClick = {
                                    viewModel.deleteUser(user.user_id)
                                    viewModel.getAllusers()
                                },
                                navController = navController
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserCardEnhanced(
    user: GetAllUsersResponseItem,
    onApproveClick: () -> Unit,
    onBlockClick: () -> Unit,
    onDeleteClick: () -> Unit,
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { isExpanded = !isExpanded },
                onLongClick = { navController.navigate(routes.UserDetailScreen(user.user_id)) }
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1B2F))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("👤 ${user.name}", style = MaterialTheme.typography.titleMedium, color = Color.White)
            Text("📧 ${user.email}", color = Color.LightGray)
            Text("📱 ${user.phone_number}", color = Color.LightGray)
            Text(
                "✅ Approved: ${if (user.isApproved == 1) "Yes" else "No"}",
                color = if (user.isApproved == 1) Color(0xFF4CAF50) else Color(0xFFFFC107),
                fontWeight = FontWeight.SemiBold
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text("🏠 ${user.address}", color = Color.Gray)
                    Text("📍 Pin: ${user.pin_code}", color = Color.Gray)
                    Text("🆔 ID: ${user.user_id}", color = Color.Gray)
                    Text("🔒 Password: ${user.user_password}", color = Color.Gray)
                    Text("📅 Created: ${user.date_of_account_created}", color = Color.Gray)

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (user.isApproved == 1) onBlockClick() else onApproveClick()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (user.isApproved == 1) Color(0xFFFF9800) else Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Icon(
                                imageVector = if (user.isApproved == 1) Icons.Default.Refresh else Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (user.isApproved == 1) "Disapprove" else "Approve", color = Color.White)
                        }

                        Button(
                            onClick = onDeleteClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Delete", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}