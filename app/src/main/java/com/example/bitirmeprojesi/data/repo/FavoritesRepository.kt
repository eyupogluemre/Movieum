package com.example.bitirmeprojesi.data.repo

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //Favori filmler listesi için StateFlow
    private val _favoriteFilms = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteFilms: StateFlow<Set<Int>> = _favoriteFilms.asStateFlow()

    //SharedPreferences instance
    private val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    init {
        //Başlangıçta kayıtlı favorileri yükleme
        loadFavorites()
    }

    //Filmi favorilere ekleme/çıkarma işlemini gerçekleştiren fonksiyon
    fun toggleFavorite(filmId: Int) {
        val currentFavorites = _favoriteFilms.value.toMutableSet()
        if (currentFavorites.contains(filmId)) {
            currentFavorites.remove(filmId)
        } else {
            currentFavorites.add(filmId)
        }
        _favoriteFilms.value = currentFavorites
        saveFavorites()
    }

    //Filmin favori olup olmadığını kontrol eden fonksiyon
    fun isFavorite(filmId: Int): Boolean {
        return _favoriteFilms.value.contains(filmId)
    }

    //Favorileri SharedPreferences'e kaydeden fonksiyon
    private fun saveFavorites() {
        sharedPreferences.edit().apply {
            putStringSet("favorite_films", _favoriteFilms.value.map { it.toString() }.toSet())
            apply()
        }
    }

    //Favorileri SharedPreferences'den yükleyen fonksiyon
    private fun loadFavorites() {
        val savedFavorites = sharedPreferences.getStringSet("favorite_films", emptySet()) ?: emptySet()
        _favoriteFilms.value = savedFavorites.mapNotNull { it.toIntOrNull() }.toSet()
    }
} 