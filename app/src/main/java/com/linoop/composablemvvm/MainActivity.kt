package com.linoop.composablemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linoop.composablemvvm.models.LoginResponse
import com.linoop.composablemvvm.ui.theme.ComposableMVVMTheme
import com.linoop.composablemvvm.ui.theme.HomeBackground
import com.linoop.composablemvvm.utils.Resource
import com.linoop.composablemvvm.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                    Login(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Login(viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var loginResponse  by remember {
        mutableStateOf<Resource<LoginResponse>>(Resource.Loading())
    }
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = HomeBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "M&S", fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Red
            )
            Text(
                text = "Technowave",
                fontWeight = FontWeight.SemiBold,
                color = Color.Magenta
            )
        }
        Column(modifier = Modifier
            .padding(horizontal = 20.dp)
            .align(Alignment.Center)) {
            Text(
                text = "India >", fontWeight = FontWeight.SemiBold,
                color = Color.Red,
                modifier = Modifier.padding(start = 10.dp, top = 20.dp, bottom = 10.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text(text = "Username") },
                placeholder = { Text(text = "Username") })
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        if (showPassword) Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_visibility_24),
                            contentDescription = ""
                        ) else Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
            )
            Text(
                text = "Forgot password?",
                color = Color.Blue,
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(3.dp)
                    .clickable { },
                textAlign = TextAlign.Right,
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                elevation = 5.dp,
            ) {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .background(Color.Magenta)
                        .clickable {
                            coroutineScope.launch {
                                loginResponse = viewModel.login(username, password)
                            }
                        }
                ) {
                    Text(text = "Login", color = Color.White)
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {
                Text(text = "Quick Login", fontSize = 18.sp, color = Color.DarkGray)
                Text(text = "Create Account", fontSize = 18.sp, color = Color.DarkGray)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            RoundImage(
                image = painterResource(id = R.drawable.apple),
                modifier = Modifier
                    .size(50.dp)
            )
            RoundImage(
                image = painterResource(id = R.drawable.facebook),
                modifier = Modifier
                    .size(50.dp)
            )
            RoundImage(
                image = painterResource(id = R.drawable.google),
                modifier = Modifier
                    .size(50.dp)
            )
        }
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

@Composable
fun RoundImage(image: Painter, modifier: Modifier = Modifier) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
            .padding(3.dp)
            .clip(CircleShape)
    )
}

/*@Composable
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
}*/

@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposableMVVMTheme {
        //Login()
    }
}