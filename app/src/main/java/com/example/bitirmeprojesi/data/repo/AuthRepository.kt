package com.example.bitirmeprojesi.data.repo

import com.example.bitirmeprojesi.data.datasource.AuthDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(private val authDataSource: AuthDataSource) {

    //Kullanıcı girişi yapan fonksiyon
    suspend fun login(email: String, password: String): Flow<Result<Unit>> = flow {
        emit(authDataSource.login(email, password))
    }

    //Yeni kullanıcı kaydı yapan fonksiyon
    suspend fun signUp(email: String, password: String): Flow<Result<Unit>> = flow {
        emit(authDataSource.signUp(email, password))
    }

    //Şifre sıfırlama e-postası gönderen fonksiyon
    suspend fun sendPasswordResetEmail(email: String): Flow<Result<Unit>> = flow {
        emit(authDataSource.sendPasswordResetEmail(email))
    }

    //Kullanıcı çıkışı yapan fonksiyon
    fun logout() {
        authDataSource.logout()
    }

    //Mevcut kullanıcı bilgisini getiren fonksiyon
    fun getCurrentUser() = authDataSource.getCurrentUser()
}
