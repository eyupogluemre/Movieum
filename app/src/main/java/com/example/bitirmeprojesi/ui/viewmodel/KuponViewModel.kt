package com.example.bitirmeprojesi.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KuponViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {
    //Kullanılabilir kupon kodları listesi
    private val kuponKodlari = listOf("ZK8B7L", "PTL74Q", "5RSW1N")

    //Çevirme butonunun durumu için StateFlow
    private val _butonAktifMi = MutableStateFlow(true)
    val butonAktifMi: StateFlow<Boolean> = _butonAktifMi.asStateFlow()

    //Animasyon durumu için StateFlow
    private val _animasyonOynuyorMu = MutableStateFlow(false)
    val animasyonOynuyorMu: StateFlow<Boolean> = _animasyonOynuyorMu.asStateFlow()

    //Kupon kodu için StateFlow
    private val _kuponKodu = MutableStateFlow("")
    val kuponKodu: StateFlow<String> = _kuponKodu.asStateFlow()

    //SharedPreferences ve FirebaseAuth tanımlamaları
    private val sharedPreferences = context.getSharedPreferences("kupon_preferences", Context.MODE_PRIVATE)
    private val auth = FirebaseAuth.getInstance()

    init {
        //Kullanıcı oturum durumu değişikliklerini dinleme
        auth.addAuthStateListener {
            resetKuponDurumu()
            loadKuponForCurrentUser()
        }
        loadKuponForCurrentUser()
    }

    //Kupon durumunu sıfırlayan fonksiyon
    private fun resetKuponDurumu() {
        _kuponKodu.value = ""
        _butonAktifMi.value = true
        _animasyonOynuyorMu.value = false
    }

    //Mevcut kullanıcının kuponunu yükleyen fonksiyon
    private fun loadKuponForCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email
            if (!userEmail.isNullOrEmpty()) {
                val kaydedilmisKupon = sharedPreferences.getString("kupon_kodu_$userEmail", null)
                if (!kaydedilmisKupon.isNullOrEmpty()) {
                    _kuponKodu.value = kaydedilmisKupon
                    _butonAktifMi.value = false
                } else {
                    _kuponKodu.value = ""
                    _butonAktifMi.value = true
                }
            } else {
                resetKuponDurumu()
            }
        } else {
            resetKuponDurumu()
        }
    }

    //Çarkı çevirme işlemini gerçekleştiren fonksiyon
    fun carkiCevir() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email
            if (!userEmail.isNullOrEmpty()) {
                viewModelScope.launch {
                    _butonAktifMi.value = false
                    _animasyonOynuyorMu.value = true
                    delay(3000)
                    _animasyonOynuyorMu.value = false
                    _kuponKodu.value = kuponKodlari.random()
                    
                    sharedPreferences.edit().putString("kupon_kodu_$userEmail", _kuponKodu.value).apply()
                }
            }
        }
    }

    //Kupon koduna göre indirim yüzdesini döndüren fonksiyon
    fun indirimYuzdesiniAl(): Int {
        return when (_kuponKodu.value) {
            "ZK8B7L" -> 25
            "PTL74Q" -> 50
            "5RSW1N" -> 75
            else -> 0
        }
    }

    override fun onCleared() {
        super.onCleared()
        //ViewModel temizlenirken Listener'ı kaldırma
        auth.removeAuthStateListener { }
    }
}