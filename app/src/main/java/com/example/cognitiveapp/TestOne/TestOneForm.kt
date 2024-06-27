package com.example.cognitiveapp.TestOne

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitiveapp.MainActivity.MainActivity
import com.example.cognitiveapp.firebase.OddOneOutViewModel
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

/**
 * A composable function for an answer button used in the Odd One Out game.
 * @param onClick The function to execute when the button is clicked.
 * @param answerText The text to display on the button.
 */
@Composable
fun AnswerButton(
    onClick: () -> Unit,
    answerText: String
) {
    Box {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xfffff07c),
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 5.dp)
        ) {
            Text(text = answerText)
        }
    }
}

/**
 * A composable function to display an image card.
 * @param drawableResource The resource ID of the drawable to display.
 * @param imageContent The description of the image content.
 */
@Composable
fun ImageCard(
    drawableResource: Int,
    imageContent: String
) {

    val localDensity = LocalDensity.current
    var cardHeight by remember {
        mutableStateOf(0.dp)
    }
    var cardWidth by remember {
        mutableStateOf(0.dp)
    }
    Card(Modifier.padding(vertical = 10.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp).fillMaxSize().onGloballyPositioned { coordinates ->
                    cardHeight = with(localDensity) { coordinates.size.height.toDp() }
                    cardWidth = with(localDensity) { coordinates.size.width.toDp() }
                },
            contentAlignment = Alignment.Center
        ) {
            val cardAspectRatio = cardWidth/cardHeight
            val shouldUseFillWidth = cardAspectRatio > 0.66f
            Image(
                painter = painterResource(id = drawableResource),
                contentDescription = "Image of a $imageContent",
                contentScale = if (shouldUseFillWidth) ContentScale.FillWidth else ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center
            )
        }
    }
}

/**
 * A composable function to display the Test One form with the current game state.
 * @param viewModel The ViewModel for the Odd One Out game.
 */
@Composable
fun TestOneForm(viewModel: OddOneOutViewModel) {
    var correctAnswer by remember { mutableStateOf(false) }
    var roundCompleted by remember { mutableStateOf(false) }
    var gameComplete by remember { mutableStateOf(false) }
    var cardHeight by remember {
        mutableStateOf(0.dp)
    }
    var cardWidth by remember {
        mutableStateOf(0.dp)
    }

    val cards = viewModel.getCurrentCards()
    val context = LocalContext.current

    fun handleAnswer(wordId: Int) {
        correctAnswer = viewModel.handleWordClick(wordId)
        roundCompleted = true
        if (!viewModel.isGameComplete()) {
            viewModel.nextRound()
            correctAnswer = false
            roundCompleted = false
        } else {
            gameComplete = true
            viewModel.saveGameResult()
        }
    }

    Column {
        Box(
            Modifier
                .padding(vertical = 15.dp)
                .weight(0.5f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Round ${viewModel.currentRound} of ${viewModel.totalRounds}"
                    )

                Text(text = "Which picture does NOT match the rest?", modifier = Modifier.padding(3.dp))
//
            }
        }

        Box(
            Modifier
                .padding(vertical = 15.dp)
                .weight(4f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .weight(1f)
                ) {
                    ImageCard(drawableResource = viewModel.getImageResourceForCard(cards[0])!!, imageContent = viewModel.getWordForNumber(cards[0].value)!!)
                    ImageCard(drawableResource = viewModel.getImageResourceForCard(cards[1])!!, imageContent = viewModel.getWordForNumber(cards[1].value)!!)
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .weight(1f)
                ) {
                    ImageCard(drawableResource = viewModel.getImageResourceForCard(cards[2])!!, imageContent = viewModel.getWordForNumber(cards[2].value)!!)
                    ImageCard(drawableResource = viewModel.getImageResourceForCard(cards[3])!!, imageContent = viewModel.getWordForNumber(cards[3].value)!!)
                }
            }
        }

        Box(
            Modifier
                .padding(vertical = 15.dp)
                .weight(2f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .weight(1f)
                ) {
                    AnswerButton(onClick = { handleAnswer(cards[0].value) }, answerText = viewModel.getWordForNumber(cards[0].value)!!)
                    AnswerButton(onClick = { handleAnswer(cards[1].value) }, answerText = viewModel.getWordForNumber(cards[1].value)!!)
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .weight(1f)
                ) {
                    AnswerButton(onClick = { handleAnswer(cards[2].value) }, answerText = viewModel.getWordForNumber(cards[2].value)!!)
                    AnswerButton(onClick = { handleAnswer(cards[3].value) }, answerText = viewModel.getWordForNumber(cards[3].value)!!)
                }
            }
        }

        if (roundCompleted) {
            if (gameComplete) {
                Text(
                    text = "Game Over! Final Score: ${viewModel.correctAnswers} / ${viewModel.totalRounds}",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    correctAnswer = false
                    roundCompleted = false
                    gameComplete = false

                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfffff07c)

                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()) {
                    Text(
                        text = "FINISH",
                        style = TextStyle(color = Color.Black, fontSize = 15.sp),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestOnePreview() {
    CognitiveAppTheme {
        TestOneForm(viewModel = OddOneOutViewModel())
    }
}
