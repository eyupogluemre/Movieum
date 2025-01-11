package com.example.bitirmeprojesi.retrofit

import com.example.bitirmeprojesi.data.entity.CRUDCevap
import com.example.bitirmeprojesi.data.entity.FilmlerCevap
import com.example.bitirmeprojesi.data.entity.SepetCevap
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FilmlerDao {

    @GET("movies/getAllMovies.php")
    suspend fun filmleriYukle() : FilmlerCevap

    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun ekle(@Field("name") name:String,
                     @Field("image") image:String,
                     @Field("price") price:Int,
                     @Field("category") category:String,
                     @Field("rating") rating:Double,
                     @Field("year") year:Int,
                     @Field("director") director:String,
                     @Field("description") description:String,
                     @Field("orderAmount") orderAmount: Int,
                     @Field("userName") userName:String) : CRUDCevap

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun sil(@Field("cartId") cartId:Int,
                    @Field("userName") userName: String) : CRUDCevap

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun sepetiGetir(@Field("userName") userName:String) : SepetCevap

}