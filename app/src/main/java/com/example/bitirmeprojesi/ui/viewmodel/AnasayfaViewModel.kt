package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.Filmler
import com.example.bitirmeprojesi.data.repo.FilmlerRepository
import com.example.bitirmeprojesi.data.repo.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnasayfaViewModel @Inject constructor(
    var filmlerRepository: FilmlerRepository,
    var favoritesRepository: FavoritesRepository
) : ViewModel() {

    //Film listesi ve favori filmler için LiveData ve StateFlow tanımlamaları
    var filmlerListesi = MutableLiveData<List<Filmler>>()
    val favoriteFilms = favoritesRepository.favoriteFilms

    //ViewModel oluşturulduğunda filmleri yükleme
    init {
        filmleriYukle()
    }

    //Tüm filmleri yükleyen fonksiyon
    fun filmleriYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            filmlerListesi.value = filmlerRepository.filmleriYukle()
        }
    }

    //Film arama işlemini gerçekleştiren fonksiyon
    fun ara(aramaKelimesi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                filmlerListesi.value = filmlerRepository.ara(aramaKelimesi)
            } catch (e: Exception) {
            }
        }
    }

    //Filmi favorilere ekleme/çıkarma işlemini gerçekleştiren fonksiyon
    fun toggleFavorite(filmId: Int) {
        favoritesRepository.toggleFavorite(filmId)
    }
}





