package com.example.bitirmeprojesi.data.repo

import com.example.bitirmeprojesi.data.datasource.FilmlerDataSource
import com.example.bitirmeprojesi.data.entity.Filmler
import com.example.bitirmeprojesi.data.entity.Sepet

class FilmlerRepository(var filmlerDataSource: FilmlerDataSource) {
    //Tüm filmleri yükleyen fonksiyon
    suspend fun filmleriYukle() : List<Filmler> = filmlerDataSource.filmleriYukle()

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
        userName:String) = filmlerDataSource.ekle(name, image, price, category, rating, year, director, description, orderAmount, userName)

    //Sepetten film silen fonksiyon
    suspend fun sil(cartId:Int, userName:String) = filmlerDataSource.sil(cartId, userName)

    //Kullanıcının sepetini getiren fonksiyon
    suspend fun sepetiGetir(userName: String) : List<Sepet> = filmlerDataSource.sepetiGetir(userName)

    //Film arama işlemini gerçekleştiren fonksiyon
    suspend fun ara(aramaKelimesi: String): List<Filmler> {
        return filmlerDataSource.ara(aramaKelimesi)
    }
}
