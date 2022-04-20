package com.linoop.composablemvvm.network

import com.linoop.composablemvvm.models.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {
    @FormUrlEncoded
    @POST("/user_app/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ):LoginResponse
}