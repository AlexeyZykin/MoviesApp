package com.alexisdev.data.network.api

import com.alexisdev.data.network.model.FilmsResponseDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val FILM_API_BASE_URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json"

interface FilmApiService {
    @GET
    fun getAllFilms(): FilmsResponseDto
}

object Retrofit {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FILM_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object FilmApiProvider {
    fun provideFilmApi(retrofit: Retrofit): FilmApiService {
        return retrofit.create(FilmApiService::class.java)
    }
}

object OkHttpClient {
    fun okhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}

object HttpInterceptor {
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}