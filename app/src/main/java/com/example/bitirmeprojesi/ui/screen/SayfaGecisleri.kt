package com.example.bitirmeprojesi.ui.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bitirmeprojesi.data.entity.Filmler
import com.example.bitirmeprojesi.ui.viewmodel.AnasayfaViewModel
import com.example.bitirmeprojesi.ui.viewmodel.AuthViewModel
import com.example.bitirmeprojesi.ui.viewmodel.DetayViewModel
import com.example.bitirmeprojesi.ui.viewmodel.KuponViewModel
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import com.google.gson.Gson

@Composable
fun SayfaGecisleri(anasayfaViewModel: AnasayfaViewModel,
                   sepetViewModel: SepetViewModel,
                   detayViewModel: DetayViewModel,
                   authViewModel: AuthViewModel,
                   kuponViewModel : KuponViewModel) {

    //Navigation controller ve state tanımlamaları
    val navController = rememberNavController()
    val navigationState by authViewModel.navigationState.collectAsState()

    //Navigation state değişikliklerini izleme
    LaunchedEffect(navigationState) {
        navigationState?.let { destination ->
            navController.navigate(destination) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
            authViewModel.resetNavigation() // Navigation gerçekleştikten sonra state'i resetleme
        }
    }

    //Navigation yapısını tanımlama
    NavHost(navController = navController, startDestination = "login") {

        //Ana sayfa rotası
        composable("anasayfa") {
            Anasayfa(navController = navController, anasayfaViewModel = anasayfaViewModel)
        }

        //Film detay sayfası rotası
        composable("detay/{film}", arguments = listOf(navArgument("film") {type= NavType.StringType}))
        {backStackEntry ->
            val json = backStackEntry.arguments?.getString("film")
            val film = Gson().fromJson(json, Filmler::class.java)

            Detay(
                navController = navController,
                gelenFilm = film,
                detayViewModel = detayViewModel
            )
        }

        //Sepet sayfası rotası
        composable("sepet/{userName}") {backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            if (!userName.isNullOrEmpty()) {
                Sepet(sepetViewModel, userName, navController)
            } else {
                Log.e("Navigasyon Hatası", "Kullanıcı adı boş veya mevcut değil!")
            }
        }

        //Giriş sayfası rotası
        composable("login") {
            Login(
                viewModel = authViewModel,
                onNavigateToSignUp = { navController.navigate("signup") },
                navController = navController
            )
        }

        //Kayıt sayfası rotası
        composable("signup") {
            SignUp(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.navigate("login") },
                navController = navController
            )
        }

        //Şifre sıfırlama sayfası rotası
        composable("sifremiunuttum") {
            SifremiUnuttum(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("sifremiunuttum") { inclusive = true }
                    }
                }
            )
        }

        //Profil sayfası rotası
        composable("profile") {
            Profil(
                navController = navController,
                viewModel = authViewModel,
            )
        }

        //Kupon sayfası rotası
        composable("kupon") {
            Kupon(
                navController = navController,
                viewModel = kuponViewModel
            )
        }
    }
}