package com.example.cognitiveapp.TestOne

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

@Composable
fun TestOneForm(){

    Surface{
        Column(
            //Modifier.horizontalScroll()
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ){
            Text(text = "In Progress test 1")

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestOnePreview() {
    CognitiveAppTheme {
        TestOneForm()
    }
}
