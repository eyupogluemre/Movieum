package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.repo.FilmlerRepository
import com.example.bitirmeprojesi.data.repo.FavoritesRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor(
    var filmlerRepository: FilmlerRepository,
    var favoritesRepository: FavoritesRepository
) : ViewModel() {

    //Sipariş adedi ve favori filmler için state tanımlamaları
    var adet = MutableLiveData(1)
    val favoriteFilms = favoritesRepository.favoriteFilms

    //Kullanıcı email bilgisini getiren fonksiyon
    fun emailAl(): String? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.email
    }

    //Toplam ücret hesaplayan fonksiyon
    fun toplamUcret(filmFiyati: Int): Int {
        return adet.value?.let { it * filmFiyati } ?: 0
    }

    //Adet değerini başlangıç durumuna getiren fonksiyon
    fun adetYapilandir() {
        adet.value = 1
    }

    //Sipariş adedini arttıran fonksiyon
    fun adetArttir() {
        adet.value = (adet.value ?: 0) + 1
    }

    //Sipariş adedini azaltan fonksiyon
    fun adetAzalt() {
        if ((adet.value ?: 0) > 1) {
            adet.value = (adet.value ?: 0) - 1
        }
    }

    //Filmi favorilere ekleme/çıkarma işlemini gerçekleştiren fonksiyon
    fun toggleFavorite(filmId: Int) {
        favoritesRepository.toggleFavorite(filmId)
    }

    //Filmi sepete ekleyen fonksiyon
    fun sepeteEkle(name: String, image: String, price: Int, category: String, rating: Double, year: Int, director: String, description: String, orderAmount: Int, userName: String) {
        viewModelScope.launch {
            try {
                //Mevcut sepeti kontrol etme
                val sepet = filmlerRepository.sepetiGetir(userName)
                val mevcutFilm = sepet.find { it.name == name }

                //Eğer film zaten sepette varsa miktarını güncelleme
                if (mevcutFilm != null) {
                    filmlerRepository.sil(mevcutFilm.cartId, userName)
                    kotlinx.coroutines.delay(500)
                    val yeniMiktar = mevcutFilm.orderAmount + orderAmount
                    filmlerRepository.ekle(name, image, price, category, rating, year, director, description, yeniMiktar, userName)
                } else {
                    //Film sepette yoksa yeni ekleme
                    filmlerRepository.ekle(name, image, price, category, rating, year, director, description, orderAmount, userName)
                }

                //Sepet durumunu güncelleme ve loglama
                kotlinx.coroutines.delay(500)
                val guncelSepet = filmlerRepository.sepetiGetir(userName)
                Log.d("Sepet Durumu", "Güncel sepet: ${guncelSepet.map { "${it.name}: ${it.orderAmount}" }}")
            } catch (e: Exception) {
                Log.e("Sepete Ekleme Hatası", "Hata: ${e.message}")
            }
        }
    }
}