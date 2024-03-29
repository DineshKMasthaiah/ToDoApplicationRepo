package com.example.todoapplication.model.login

import com.example.todoapplication.model.api.BackendCallback
import com.example.todoapplication.model.api.BackendResult
import com.example.todoapplication.model.api.TDApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TDLoginService {
    fun doLogin(loginRequest: TDLoginRequest, callback: BackendCallback<TDLoginResponse>) {
        TDApiClient.service.login(loginRequest).enqueue(object :
            Callback<TDLoginResponse> {

            override fun onResponse(
                call: Call<TDLoginResponse>,
                response: Response<TDLoginResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(BackendResult.Success(it))
                    } ?: callback(BackendResult.Error(response.message()))
                } else {
                    callback(BackendResult.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<TDLoginResponse>, t: Throwable) {
                callback(BackendResult.Error(t.localizedMessage ?: ""))
            }
        })
    }
}
