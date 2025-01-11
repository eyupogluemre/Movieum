package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.entity.Sepet
import com.example.bitirmeprojesi.data.repo.FilmlerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth

@HiltViewModel
class SepetViewModel @Inject constructor(var filmlerRepository: FilmlerRepository) : ViewModel() {

//Sepetteki ürünler listesi için LiveData tanımlaması
var sepetListesi = MutableLiveData<List<Sepet>>()

//Kullanıcı email bilgisini getiren fonksiyon
private fun emailAl(): String? {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser?.email
}

//Sepetten ürün silme işlemini gerçekleştiren fonksiyon
fun sil(cartId:Int, userName:String) {
    viewModelScope.launch {
        try {
            filmlerRepository.sil(cartId, userName)
            sepetiGetir(userName)
        } catch (e:Exception) {
            Log.e("Silme Hatası", "Hata: ${e.message}")
        }
    }
}

//Kullanıcının sepetini getiren fonksiyon
fun sepetiGetir(userName: String) {
    viewModelScope.launch {
        try {
            val sepetFilmleri = filmlerRepository.sepetiGetir(userName)
            if (sepetFilmleri.isEmpty()) {
                sepetListesi.value = emptyList()
                Log.d("Sepet", "Sepetin Boş!")
            } else {
                sepetListesi.value = ayniFilmleriGrupla(sepetFilmleri)
            }
        } catch (e: Exception) {
            Log.e("Arama Hatası", "Hata: ${e.message}")
            e.printStackTrace()
        }
    }
}

//Aynı filmleri gruplayıp miktarlarını toplayan fonksiyon
fun ayniFilmleriGrupla(sepetFilmleri : List<Sepet>) : List<Sepet> {
    return sepetFilmleri.groupBy { it.name } .map { entry ->
        val film = entry.value.first()
        val toplamAdet = entry.value.sumOf { it.orderAmount }
        film.copy(orderAmount = toplamAdet)
    }
}

//Sepeti tamamen temizleyen fonksiyon
fun sepetiTemizle(userName: String) {
    viewModelScope.launch {
        try {
            val sepetFilmleri = filmlerRepository.sepetiGetir(userName)

            for (film in sepetFilmleri) {
                filmlerRepository.sil(film.cartId, userName)
            }
            sepetiGetir(userName)
        } catch (e:Exception) {
            Log.e("Temizleme Hatası", "Hata: ${e.message}")
        }
    }
}

//ViewModel başlatıldığında sepeti periyodik olarak güncelleme
init {
    viewModelScope.launch {
        while(true) {
            //Her 2 saniyede bir sepeti güncelleme
            delay(2000)
            emailAl()?.let { sepetiGetir(it) }
        }
    }
}

}