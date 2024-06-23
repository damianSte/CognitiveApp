package com.example.cognitiveapp.TestThree

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun WordText(
    word: String
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = word, fontWeight = FontWeight(700))
    }
}

@Composable
fun TestThreeForm(viewModel: WordsGameViewModel, navController: NavHostController) {
    val words by viewModel.words.observeAsState(emptyList())
    val navigateToAnswerForm by viewModel.navigateToAnswerForm.observeAsState(false)
    val remainingTime by viewModel.remainingTime.observeAsState(initial = 0)

    if (navigateToAnswerForm) {
        navController.navigate("TestThreeAnswerForm")
        viewModel.onAnswerFormNavigated()
    }

    val countdownDurationMillis: Long = 45000

    val progress = ((countdownDurationMillis - remainingTime) / countdownDurationMillis.toFloat())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Remember these 5 words - you will have to write them on the next screen.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }


        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    color = Color(0xff87bba2),
                    modifier = Modifier.fillMaxWidth().height(8.dp)
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Time remaining: ${remainingTime / 1000} seconds",
                    color = Color.Black
                )
            }
        }

        words.forEachIndexed { index, word ->
            Box(
                Modifier
                    .padding(vertical = 10.dp)
                    .weight(1f)
            ) {
                WordText(word = word)
            }
        }
    }
}
