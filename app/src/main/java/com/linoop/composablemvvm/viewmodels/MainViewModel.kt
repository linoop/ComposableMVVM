package com.linoop.composablemvvm.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.linoop.composablemvvm.models.LoginResponse
import com.linoop.composablemvvm.models.User
import com.linoop.composablemvvm.repository.MyRepo
import com.linoop.composablemvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MyRepo) : ViewModel() {
    /*suspend fun login(username:String, password:String):Resource<LoginResponse>{
        return repo.login(username = username, password = password)
    }*/

    //lateinit var navController: NavController

    var response = MutableStateFlow("")

    private val TAG = "MainViewModel"
    fun login(username:String, password:String){
        viewModelScope.launch {
            val loginResponse = repo.login(username = username, password = password)

            when (loginResponse) {
                is Resource.Success -> {
                    Log.d(TAG, loginResponse.data.toString())
                    response.value = loginResponse.data?.token.toString()
                    //navController.navigate("home_view/${loginResponse.data?.token}")
                }
                is Resource.Error -> {
                    Log.d(TAG, "Error")
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading....")
                }
            }
        }
    }
}