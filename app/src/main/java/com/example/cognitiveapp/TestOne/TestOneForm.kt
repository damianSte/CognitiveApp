package com.example.cognitiveapp.TestOne

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cognitiveapp.R
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

// is reset necessary here?

//@Composable
//fun ResetGameButton(
//    modifier: Modifier = Modifier
//) {
//
//    IconButton(
//        onClick = {},
//        icon = Icons.Default.Cached,
//        contentDescription = "Reset Game",
//        tint = Color.Black,
//        modifier = modifier
//    )
//}

@Composable
fun AnswerButton(
    onClick: () -> Unit,
    answerText: String
){
    Box() {
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

@Composable
fun ImageCard(
    drawableResource: Int,
    imageContent: String
){
    var cardHeight by remember {
        mutableStateOf(0.dp)
    }
    var cardWidth by remember {
        mutableStateOf(0.dp)
    }

    Card(Modifier.padding(vertical = 10.dp)) {
        Box(
            modifier = Modifier.height(200.dp).width(200.dp),
            contentAlignment = Alignment.Center
        ) {
            val cardAspectRatio = cardWidth / cardHeight
            val shouldUseFillWidth = cardAspectRatio > 0.66f
            Image(
                painter = painterResource(id = drawableResource),
                contentDescription = "Image of a $imageContent",
                contentScale =
                if (shouldUseFillWidth) ContentScale.FillWidth else ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center
            )

        }
    }
}

@Composable
fun TestOneForm(){

    Column {
        Box(Modifier.padding(vertical = 15.dp).weight(0.5f)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp)
            ) {
                Text(text = "Which picture does NOT match the rest?")
            }
        }

        Box(Modifier.padding(vertical = 15.dp).weight(4f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 15.dp).weight(1f)
                ) {
                    ImageCard(drawableResource = R.drawable.apple, imageContent = "Apple")
                    ImageCard(drawableResource = R.drawable.rabbit, imageContent = "Rabbit")
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 15.dp).weight(1f)
                ) {
                    ImageCard(drawableResource = R.drawable.cat, imageContent = "Cat")
                    ImageCard(drawableResource = R.drawable.dog, imageContent = "Dog")
                }

            }
        }

        Box(Modifier.padding(vertical = 15.dp).weight(2f)) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 15.dp).weight(1f)
                ) {
                    AnswerButton(onClick = { /*TODO*/ }, answerText = "Rabbit")
                    AnswerButton(onClick = { /*TODO*/ }, answerText = "Dog")
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 15.dp).weight(1f)
                ) {
                    AnswerButton(onClick = { /*TODO*/ }, answerText = "Apple")
                    AnswerButton(onClick = { /*TODO*/ }, answerText = "Cat")
                }
            }
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