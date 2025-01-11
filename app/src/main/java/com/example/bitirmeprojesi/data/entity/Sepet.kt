package com.example.bitirmeprojesi.data.entity

data class Sepet(var cartId:Int,
                 var name:String,
                 var image:String,
                 var price:Int,
                 var category:String,
                 var rating:Double,
                 var year:Int,
                 var director:String,
                 var description:String,
                 var orderAmount:Int,
                 var userName:String) {
}