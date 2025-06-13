package com.example.lab1.ui.screen

// ui/screen/LoginScreen.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In Form",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Not account yet? Create one")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "OR")

        Spacer(modifier = Modifier.height(16.dp))

        // Trong LoginScreen.kt, sửa phần Button onClick:
        Button(
            onClick = {
                android.util.Log.d("LoginScreen", "Login button clicked")
                android.util.Log.d("LoginScreen", "Username: $username, Password: $password")
                // Kiểm tra username và password đơn giản
                if (username == "admin" && password == "1234") {
                    android.util.Log.d("LoginScreen", "Login successful, calling onLoginSuccess")
                    onLoginSuccess()
                } else {
                    android.util.Log.d("LoginScreen", "Login failed")
                    // Có thể hiện thông báo lỗi
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIGN IN")
        }
    }
}
