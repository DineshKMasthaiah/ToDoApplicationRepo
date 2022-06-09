package com.example.todoapplication.model.api

data class TDGenericRequest(val header: TDHeader, val body: TDBody) {
    data class TDHeader(val headers: MutableMap<String, String>)
    data class TDBody(val json: String)
}
