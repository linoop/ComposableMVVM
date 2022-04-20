package com.linoop.composablemvvm.repository

import com.linoop.composablemvvm.models.LoginResponse
import com.linoop.composablemvvm.network.MyApi
import com.linoop.composablemvvm.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class MyRepo @Inject constructor(private val api: MyApi) {
    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        val response = try {
            api.login(username, password)
        } catch (e: Exception) {
            return Resource.Error("An error occurred")
        }
        return Resource.Success(response)
    }
}