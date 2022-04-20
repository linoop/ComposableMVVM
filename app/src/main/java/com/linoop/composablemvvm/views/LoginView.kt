package com.linoop.composablemvvm.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.linoop.composablemvvm.models.LoginResponse
import com.linoop.composablemvvm.utils.Resource
import com.linoop.composablemvvm.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginPage(viewModel: MainViewModel) {
    /*val loginResponse = produceState<Resource<LoginResponse>>(initialValue = Resource.Loading()){
        value = viewModel.login("admin", "admin")
    }.value*/
    var loginResponse  by remember {
        mutableStateOf<Resource<LoginResponse>>(Resource.Loading())
    }
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Login")
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Username") }, modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Password") }, modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            coroutineScope.launch {
                loginResponse = viewModel.login(username, password)
            }
        }) {
            Text(text = "Login")
        }
        when (loginResponse) {
            is Resource.Success -> {
                Text(text = "Hello ${loginResponse.data?.token}!")
            }
            is Resource.Error -> {
                Text(text = " Error!")
            }
            is Resource.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(10.dp)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                )
            }
        }
    }
}
