package com.linoop.composablemvvm.network

import com.linoop.composablemvvm.models.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {
    //192.168.1.114:8000/api/user/login
    @FormUrlEncoded
    @POST("api/user/login")
    suspend fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ):LoginResponse
}