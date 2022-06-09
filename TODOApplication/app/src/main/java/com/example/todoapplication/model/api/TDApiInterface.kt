package com.example.todoapplication.model.api

import com.example.todoapplication.model.login.TDLoginRequest
import com.example.todoapplication.model.login.TDLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TDApiInterface {
    @POST("login")
    fun login(@Body loginRequest: TDLoginRequest): Call<TDLoginResponse>
}