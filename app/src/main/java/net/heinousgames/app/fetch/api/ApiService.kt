package net.heinousgames.app.fetch.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    fun getHiringList(): Call<List<HiringObject>>
}