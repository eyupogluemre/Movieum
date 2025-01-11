package com.example.bitirmeprojesi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.bitirmeprojesi.ui.screen.SayfaGecisleri
import com.example.bitirmeprojesi.ui.theme.BitirmeProjesiTheme
import com.example.bitirmeprojesi.ui.viewmodel.AnasayfaViewModel
import com.example.bitirmeprojesi.ui.viewmodel.AuthViewModel
import com.example.bitirmeprojesi.ui.viewmodel.DetayViewModel
import com.example.bitirmeprojesi.ui.viewmodel.KuponViewModel
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val anasayfaViewModel : AnasayfaViewModel by viewModels()
    val detayViewModel : DetayViewModel by viewModels()
    val sepetViewModel : SepetViewModel by viewModels()
    val authViewModel : AuthViewModel by viewModels()
    val kuponViewModel : KuponViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        insetsController.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            BitirmeProjesiTheme {
                SayfaGecisleri(
                    anasayfaViewModel = anasayfaViewModel,
                    detayViewModel = detayViewModel,
                    sepetViewModel = sepetViewModel,
                    authViewModel = authViewModel,
                    kuponViewModel = kuponViewModel
                )
            }
        }
    }
}