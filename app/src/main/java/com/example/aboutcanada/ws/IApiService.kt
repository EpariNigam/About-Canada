package com.example.aboutcanada.ws

import com.example.aboutcanada.model.Fact
import retrofit2.Call
import retrofit2.http.GET

interface IApiService {
    @GET("facts.json")
    fun facts(): Call<Fact>
}