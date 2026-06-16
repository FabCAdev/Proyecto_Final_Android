package com.example.proyecto_final.data.network

import com.example.proyecto_final.data.model.AuthResponse
import com.example.proyecto_final.data.model.GameResponse
import com.example.proyecto_final.data.model.LoginRequest
import com.example.proyecto_final.data.network.RetrofitClient.JSON_MEDIA_TYPE
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // Auth endpoints
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    // Games endpoints
    @GET("api/games")
    suspend fun getGames(@Query("genre") genre: String? = null): List<GameResponse>
}

object RetrofitClient {
    // Cambiamos el localhost local por tu API real en la web
    private const val BASE_URL = "https://examenfinalapiandrois.onrender.com/"
    val JSON_MEDIA_TYPE = "application/json".toMediaType()

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
    }

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConfig.asConverterFactory(JSON_MEDIA_TYPE))
            .build()
            .create(ApiService::class.java)
    }
}