package com.example.bitirmeprojesi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.ui.viewmodel.AuthState
import com.example.bitirmeprojesi.ui.viewmodel.AuthViewModel

@Composable
fun SignUp(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    navController: NavController
) {
    //Kayıt formu için state tanımlamaları
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

    //Başarılı kayıt sonrası direkt navigate etme
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate("anasayfa") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = Color(0xff190e36))
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "MOVIEUM",
            color = Color(0xffb8b7bf),
            style = MaterialTheme.typography.displayLarge
        )

        Image(painter = painterResource(R.drawable.movie), contentDescription = "", modifier = Modifier.size(width = 250.dp, height = 250.dp))



        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-Posta") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffe7e0ea),
                unfocusedContainerColor = Color(0xffe7e0ea),
                focusedLabelColor = Color(0xff48444c),
                unfocusedLabelColor = Color(0xff48444c),
                focusedTextColor = Color(0xff48444c),
                unfocusedTextColor = Color(0xff48444c),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xff48444c)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffe7e0ea),
                unfocusedContainerColor = Color(0xffe7e0ea),
                focusedLabelColor = Color(0xff48444c),
                unfocusedLabelColor = Color(0xff48444c),
                focusedTextColor = Color(0xff48444c),
                unfocusedTextColor = Color(0xff48444c),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xff48444c)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff2d204b),
                contentColor = Color(0xffcccbd3)),
            onClick = { viewModel.signUp(email, password) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Kaydol", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { }) {
            Text("Hesabın var mı?", color = Color(0xffb8b7bf), style = MaterialTheme.typography.bodyLarge)
        }
        TextButton(onClick = onNavigateToLogin) {
            Text("Giriş yap!", color = Color(0xffb8b7bf), style = MaterialTheme.typography.bodyLarge)
        }

        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator(color = Color(0xffb8b7bf))
            is AuthState.Success -> Text((authState as AuthState.Success).message, color = Color(0xffb8b7bf))
            is AuthState.Error -> Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }
    }
}