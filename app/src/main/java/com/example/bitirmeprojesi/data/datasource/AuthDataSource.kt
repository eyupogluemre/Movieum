package com.example.bitirmeprojesi.data.datasource

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

//Firebase Authentication işlemlerini yöneten sınıf
class AuthDataSource {

    //Firebase Auth instance
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Kullanıcı girişi yapan fonksiyon
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //Yeni kullanıcı kaydı yapan fonksiyon
    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //Şifre sıfırlama e-postası gönderen fonksiyon
    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //Kullanıcı çıkışı yapan fonksiyon
    fun logout() {
        auth.signOut()
    }

    //Mevcut kullanıcı bilgisini getiren fonksiyon
    fun getCurrentUser() = auth.currentUser
}