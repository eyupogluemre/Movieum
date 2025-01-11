package com.example.bitirmeprojesi.data.datasource

import android.util.Log
import com.example.bitirmeprojesi.data.entity.Filmler
import com.example.bitirmeprojesi.data.entity.Sepet
import com.example.bitirmeprojesi.retrofit.FilmlerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmlerDataSource(var filmlerDao: FilmlerDao) {

    //Tüm filmleri yükleyen fonksiyon
    suspend fun filmleriYukle() : List<Filmler> = withContext(Dispatchers.IO) {
        return@withContext filmlerDao.filmleriYukle().movies
    }

    //Sepete film ekleyen fonksiyon
    suspend fun ekle(
        name:String,
        image:String,
        price:Int,
        category:String,
        rating:Double,
        year:Int,
        director:String,
        description:String,
        orderAmount: Int,
        userName:String) {
        filmlerDao.ekle(name, image, price, category, rating, year, director, description, orderAmount, userName)
    }

    //Kullanıcının sepetini getiren fonksiyon
    suspend fun sepetiGetir(userName:String) : List<Sepet> = withContext(Dispatchers.IO) {
        return@withContext try {
            val degis = filmlerDao.sepetiGetir(userName)
            degis.movie_cart
        } catch (e: Exception) {
            Log.e("Arama Hatası", e.toString())
            emptyList()
        }
    }

    //Sepetten film silen fonksiyon
    suspend fun sil(cartId: Int, userName:String): Result<Unit> {
        return try {
            filmlerDao.sil(cartId, userName)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Film Silme Hatası", e.toString())
            Result.failure(e)
        }
    }

    //Film arama işlemini gerçekleştiren fonksiyon
    suspend fun ara(aramaKelimesi: String): List<Filmler> {
        val response = filmlerDao.filmleriYukle() // API'den tüm filmleri al
        return response.movies.filter {
            it.name.contains(aramaKelimesi, ignoreCase = true)
        }
    }
}


