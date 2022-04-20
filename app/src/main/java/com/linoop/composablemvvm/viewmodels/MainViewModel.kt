package com.linoop.composablemvvm.viewmodels

import androidx.lifecycle.ViewModel
import com.linoop.composablemvvm.models.LoginResponse
import com.linoop.composablemvvm.models.User
import com.linoop.composablemvvm.repository.MyRepo
import com.linoop.composablemvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MyRepo) : ViewModel() {
    suspend fun login(username:String, password:String):Resource<LoginResponse>{
        return repo.login(username = username, password = password)
    }
}