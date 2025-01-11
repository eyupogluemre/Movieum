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
import androidx.compose.ui.unit.dp
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.ui.viewmodel.AuthState
import com.example.bitirmeprojesi.ui.viewmodel.AuthViewModel

@Composable
fun SifremiUnuttum(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
) {
    //Şifre sıfırlama formu için state tanımlamaları
    var email by remember { mutableStateOf("") }
    val resetPasswordState by viewModel.resetPasswordState.collectAsState()
    var showSuccessMessage by remember { mutableStateOf(false) }

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

        Image(
            painter = painterResource(R.drawable.movie),
            contentDescription = "",
            modifier = Modifier.size(width = 250.dp, height = 250.dp)
        )

        Text(
            text = "Şifre Sıfırlama",
            color = Color(0xffb8b7bf),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Kayıtlı E-Posta Adresini İlgili Alana Gir",
            color = Color(0xffb8b7bf),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (showSuccessMessage) {
            Text(
                text = "Şifre sıfırlama e-postası gönderildi. Lütfen e-posta adresinizi kontrol et!",
                color = Color(0xffb8b7bf),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff2d204b),
                    contentColor = Color(0xffcccbd3)
                ),
                onClick = {
                    viewModel.resetStates()
                    onNavigateToLogin()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Giriş Sayfasına Dön", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff2d204b),
                    contentColor = Color(0xffcccbd3)
                ),
                onClick = {
                    viewModel.sendPasswordResetEmail(email)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Şifre Sıfırlama E-Postası Gönder", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    viewModel.resetStates()
                    onNavigateToLogin()
                }
            ) {
                Text("Giriş ekranına dön!", color = Color(0xffb8b7bf), style = MaterialTheme.typography.bodyLarge)
            }
        }

        //Başarılı şifre sıfırlama durumunu kontrol etme
        LaunchedEffect(resetPasswordState) {
            if (resetPasswordState is AuthState.Success) {
                showSuccessMessage = true
            }
        }

        //Şifre sıfırlama durumuna göre UI gösterimi
        when (resetPasswordState) {
            is AuthState.Loading -> CircularProgressIndicator(color = Color(0xffb8b7bf))
            is AuthState.Error -> Text(
                text = (resetPasswordState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }
    }
}