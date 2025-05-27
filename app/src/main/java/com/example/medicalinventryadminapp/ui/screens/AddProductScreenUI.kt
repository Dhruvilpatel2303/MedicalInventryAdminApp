package com.example.medicalinventryadminapp.ui.screens


import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.medicalinventryadminapp.navigation.routes
import com.example.medicalinventryadminapp.viewModel.MainViewModel
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreenUI(viewModel: MainViewModel,navController: NavController) {

    val productState = viewModel.createproductstate.collectAsState().value
    val productName = remember { mutableStateOf("") }
    val productPrice = remember { mutableStateOf("") }
    val productQuantity = remember { mutableStateOf("") }
    val productCategory = remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFF121212),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Product",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
                },
                navigationIcon = {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.clickable {
                        navController.navigate(routes.AllProductsScreen)
                    })
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1F2B)),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter Product Details",
                        color = Color(0xFFF5FF40),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    InputTextFieldWithIcon(
                        label = "Product Name",
                        value = productName.value,
                        onValueChange = { productName.value = it },

                    )

                    InputTextFieldWithIcon(
                        label = "Product Price",
                        value = productPrice.value,
                        onValueChange = { productPrice.value = it },

                    )

                    InputTextFieldWithIcon(
                        label = "Product Quantity",
                        value = productQuantity.value,
                        onValueChange = { productQuantity.value = it },

                    )

                    InputTextFieldWithIcon(
                        label = "Product Category",
                        value = productCategory.value,
                        onValueChange = { productCategory.value = it },

                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            if (productName.value.isNotEmpty() && productPrice.value.isNotEmpty()
                                && productQuantity.value.isNotEmpty() && productCategory.value.isNotEmpty()
                            ) {
                                viewModel.createProduct(
                                    productName.value,
                                    productPrice.value,
                                    productQuantity.value,
                                    productCategory.value
                                )
                                productName.value = ""
                                productPrice.value = ""
                                productQuantity.value = ""
                                productCategory.value = ""

                                Toast.makeText(context, "Product Added!", Toast.LENGTH_SHORT).show()
                                navController.navigate(routes.AllProductsScreen)
                            } else {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                navController.navigate(routes.AllProductsScreen)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6E40)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                    ) {
                        Text("ADD PRODUCT", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
fun InputTextFieldWithIcon(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,

) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFFBBDEFB)) },
      placeholder = { Text("Enter $label", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 18.sp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF64DD17),
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color(0xFF64DD17),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.LightGray,
            focusedLabelColor = Color(0xFFBBDEFB),
            focusedContainerColor = Color(0xFF20232A),
            unfocusedContainerColor = Color(0xFF1C1C27)
        ),
        singleLine = true
    )
}