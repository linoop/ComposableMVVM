package com.linoop.composablemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linoop.composablemvvm.models.LoginResponse
import com.linoop.composablemvvm.ui.theme.ComposableMVVMTheme
import com.linoop.composablemvvm.utils.Resource
import com.linoop.composablemvvm.viewmodels.MainViewModel
import com.linoop.composablemvvm.views.LoginPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposableMVVMTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginPage(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: MainViewModel) {
    val loginResponse = produceState<Resource<LoginResponse>>(initialValue = Resource.Loading()) {
        value = viewModel.login("admin", "admin")
    }.value

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
                    .size(100.dp)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
        }
    }
}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExtendedText(string = "ABC")
        ExtendedText(string = "XYZ")
    }
}

@Composable
fun ExtendedText(string: String) {
    Text(text = string, color = Color.Blue,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposableMVVMTheme {
        HomePage()
    }
}