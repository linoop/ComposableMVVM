package com.linoop.composablemvvm.network

import com.linoop.composablemvvm.storage.SharedPrefManager
import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor(private val preferences: SharedPrefManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        preferences.getToken()?.let { request.header("Authorization", "Token $it") }
        return chain.proceed(request.build())
    }
}