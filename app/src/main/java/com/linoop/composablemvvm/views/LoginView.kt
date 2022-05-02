package com.linoop.composablemvvm.views

import android.util.Log
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.linoop.composablemvvm.R
import com.linoop.composablemvvm.models.LoginResponse
import com.linoop.composablemvvm.ui.theme.HomeBackground
import com.linoop.composablemvvm.utils.Resource
import com.linoop.composablemvvm.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun Login(viewModel: MainViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var loginResponse by remember {
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
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.Center)
        ) {
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
                            viewModel.login(username, password)
                            Log.d("LoginOnClick", "Email $username password $password")

                            //coroutineScope.launch {
                            //loginResponse = viewModel.login(username, password)
                            //}
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

