package net.heinousgames.app.fetch.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

class Client {

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(
                Json.asConverterFactory(
                    MediaType.get("application/json; charset=UTF8")
                )
            )
            .build()
    }

    fun getApiService(): ApiService {
        return getRetrofitInstance().create<ApiService>()
    }
}