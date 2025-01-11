package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Kimlik doğrulama durumlarını temsil eden sealed class
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

//Kimlik doğrulama işlemleri için ViewModel sınıfı
@HiltViewModel
class AuthViewModel @Inject constructor(
    var authRepository: AuthRepository
) : ViewModel() {

    //Navigasyon durumu için StateFlow
    private val _navigationState = MutableStateFlow<String?>(null)
    val navigationState: StateFlow<String?> = _navigationState

    //Kimlik doğrulama durumu için StateFlow
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    //Şifre sıfırlama durumu için StateFlow
    private val _resetPasswordState = MutableStateFlow<AuthState>(AuthState.Idle)
    val resetPasswordState = _resetPasswordState.asStateFlow()

    init {
        //Uygulama başladığında kullanıcı oturum durumunu kontrol etme
        authRepository.getCurrentUser()?.let {
            _navigationState.value = "anasayfa"
        }
    }

    //Giriş işlemini gerçekleştiren fonksiyon
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.login(email, password).collect { result ->
                result.onSuccess {
                    _authState.value = AuthState.Success("Giriş başarılı!")
                    _navigationState.value = "anasayfa"
                }.onFailure {
                    _authState.value = AuthState.Error(it.message ?: "Giriş işlemi başarısız oldu.")
                }
            }
        }
    }

    //Çıkış işlemini gerçekleştiren fonksiyon
    fun logout() {
        authRepository.logout()
        _navigationState.value = "login"
        _authState.value = AuthState.Idle
    }

    //Navigasyon durumunu sıfırlayan fonksiyon
    fun resetNavigation() {
        _navigationState.value = null
    }

    //Kayıt işlemini gerçekleştiren fonksiyon
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.signUp(email, password).collect { result ->
                result.onSuccess {
                    _authState.value = AuthState.Success("Kayıt başarılı!")
                    _navigationState.value = "anasayfa"
                }.onFailure {
                    _authState.value = AuthState.Error(it.message ?: "Kayıt işlemi başarısız oldu.")
                }
            }
        }
    }

    //Şifre sıfırlama e-postası gönderen fonksiyon
    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _resetPasswordState.value = AuthState.Loading
            authRepository.sendPasswordResetEmail(email).collect { result ->
                result.onSuccess {
                    _resetPasswordState.value = AuthState.Success("Şifre sıfırlama e-postası gönderildi")
                }.onFailure {
                    _resetPasswordState.value = AuthState.Error(it.message ?: "Bir hata oluştu")
                }
            }
        }
    }

    //Tüm durumları sıfırlayan fonksiyon
    fun resetStates() {
        _authState.value = AuthState.Idle
        _resetPasswordState.value = AuthState.Idle
    }
}
