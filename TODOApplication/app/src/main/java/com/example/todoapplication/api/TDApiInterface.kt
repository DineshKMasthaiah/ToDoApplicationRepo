package com.example.todoapplication.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TDApiInterface {
    @POST("login")
    fun login(@Body loginRequest: TDLoginRequest): Call<TDLoginResponse>
}