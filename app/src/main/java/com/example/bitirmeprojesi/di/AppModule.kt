package com.example.bitirmeprojesi.di

import android.content.Context
import com.example.bitirmeprojesi.data.datasource.AuthDataSource
import com.example.bitirmeprojesi.data.datasource.FilmlerDataSource
import com.example.bitirmeprojesi.data.repo.AuthRepository
import com.example.bitirmeprojesi.data.repo.FilmlerRepository
import com.example.bitirmeprojesi.retrofit.ApiUtils
import com.example.bitirmeprojesi.retrofit.FilmlerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideFilmlerRepository(filmlerDataSource: FilmlerDataSource) : FilmlerRepository {
        return FilmlerRepository(filmlerDataSource)
    }

    @Provides
    @Singleton
    fun provideFilmlerDataSource(filmlerDao: FilmlerDao) : FilmlerDataSource {
        return FilmlerDataSource(filmlerDao)
    }

    @Provides
    @Singleton
    fun provideFilmlerDao() : FilmlerDao {
        return ApiUtils.getFilmlerDao()
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(): AuthDataSource {
        return AuthDataSource()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource) : AuthRepository {
        return AuthRepository(authDataSource)
    }
}