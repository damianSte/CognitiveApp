package com.example.cognitiveapp.MainActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitiveapp.R
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme


/**
 * Composable function that displays the main UI of the MainActivity.
 *
 * @param onTestClick Callback function to handle when a test button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityForm(onTestClick: (testNumber: Int) -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cognitive App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxSize()
            ) {

                Text(
                    text = "Select Test",
                    style = TextStyle(fontSize = 35.sp),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Button(
                    onClick = { onTestClick(1) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfffff07c)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        val image: Painter = painterResource(id = R.drawable.puzzle)
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 10.dp)
                        )
                                        Text(
                                    text = "ODD ONE OUT",
                                    style = TextStyle(color = Color.Black, fontSize = 15.sp),
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onTestClick(2) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfffa824c)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        val image: Painter = painterResource(id = R.drawable.aces)
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 10.dp)
                        )
                        Text(
                            text = "MEMORY CARDS",
                            style = TextStyle(color = Color.Black, fontSize = 15.sp),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onTestClick(3) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff87bba2)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        val image: Painter = painterResource(id = R.drawable.icon2)
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 10.dp)
                        )
                        Text(
                            text = "REMEMBER WORDS",
                            style = TextStyle(color = Color.Black, fontSize = 15.sp),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActivityPreview() {
    CognitiveAppTheme {
        MainActivityForm { }
    }
}
