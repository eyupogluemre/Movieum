package com.example.bitirmeprojesi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bitirmeprojesi.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profil(
    navController: NavController,
    viewModel: AuthViewModel
) {
    //Çıkış dialog durumu için state tanımlaması
    var showLogoutDialog by remember { mutableStateOf(false) }

    val navigationState by viewModel.navigationState.collectAsState()

    //NavigationState değiştiğinde login sayfasına yönlendirme
    LaunchedEffect(navigationState) {
        if (navigationState == "login") {
            navController.navigate("login") {
                popUpTo("anasayfa") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(
                        WindowInsetsSides.Top)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff190e36),
                    titleContentColor = Color(0xffb8b7bf),
                    actionIconContentColor = Color(0xffb8b7bf),
                    navigationIconContentColor = Color(0xffb8b7bf)
                ),
                title = {
                    Text(
                        "Profil",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color.Black.copy(alpha = 0.4f)
                ) {
                }
                BottomBar(navController = navController)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = Color(0xff190e36))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xff2d204b)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "E-Posta Adresi:",
                            color = Color(0xffb8b7bf),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = viewModel.authRepository.getCurrentUser()?.email ?: "E-Posta Bulunamadı!",
                            color = Color(0xffcccbd3),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Button(
                    onClick = { showLogoutDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff2d204b),
                        contentColor = Color(0xffcccbd3)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Çıkış Yap", fontSize = 15.sp)
                }
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = {
                    Text(
                        "Çıkış Yap",
                        color = Color(0xffb8b7bf),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(
                        "Çıkış yapmak istediğinden emin misin?",
                        color = Color(0xffb8b7bf),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.logout()
                            showLogoutDialog = false
                        }
                    ) {
                        Text(
                            "Evet",
                            color = Color(0xffb8b7bf),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showLogoutDialog = false }
                    ) {
                        Text(
                            "Hayır",
                            color = Color(0xffb8b7bf),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                containerColor = Color(0xff2d204b),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}