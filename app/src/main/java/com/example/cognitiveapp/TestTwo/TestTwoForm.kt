package com.example.cognitiveapp.TestTwo

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitiveapp.MainActivity.MainActivity
import com.example.cognitiveapp.TestTwo.Game.MemoryCard
import com.example.cognitiveapp.TestTwo.Game.MemoryEvent
import com.example.cognitiveapp.TestTwo.Game.MemoryState
import com.example.cognitiveapp.TestTwo.Game.MemoryViewModel
import com.example.cognitiveapp.firebase.MemoryGameDataClass
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryGameCard(
    card: MemoryCard,
    modifier: Modifier,
    state: MemoryState,
    onCLick: () -> Unit
) {
    if (card.isBackDisplayed) {
        val localDensity = LocalDensity.current
        var cardHeight by remember {
            mutableStateOf(0.dp)
        }
        var cardWidth by remember {
            mutableStateOf(0.dp)
        }
        Card(
            shape = RoundedCornerShape(10.dp),
            onClick = onCLick,
            colors = CardDefaults.cardColors(containerColor = state.theme.cardBaseColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        cardHeight = with(localDensity) { coordinates.size.height.toDp() }
                        cardWidth = with(localDensity) { coordinates.size.width.toDp() }
                    },
                contentAlignment = Alignment.Center
            ) {

                val cardAspectRatio = cardWidth / cardHeight
                val shouldUseFillWidth = cardAspectRatio > 0.66f
                Image(
                    painter = painterResource(id = state.theme.cardBack),
                    contentDescription = "Back of card Image",
                    contentScale =
                    if (shouldUseFillWidth) ContentScale.FillWidth else ContentScale.FillHeight,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )

            }

        }
    }else{
        val borderModifier = if(card.machFound){
            modifier.border(
                width = 4.dp,
                shape = RoundedCornerShape(10.dp),
                color = state.theme.matchedOutlineColor
            )
        }else{
            modifier
        }
        OutlinedCard(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = state.theme.cardFrontBaseColor
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = borderModifier
        ) {
            Box(modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
             contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = state.theme.getImageResourceForNumber(card.value)!!),
                    modifier = modifier.fillMaxSize(),
                    contentDescription = "memory card ${card.value}",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
            }

        }
    }
}

fun calculateRowRange(pairCount: Int, isPortrait: Boolean): List<IntRange>{
    return when(isPortrait){
        true->{
            when(pairCount){
                6-> listOf(0.. 2,3..5,6..8, 9..11)
                else -> emptyList()
            }
        }false ->{
            when (pairCount){
                6-> listOf(0..3,4..7,8..11)
                else -> emptyList()
            }
        }
    }
}

@Composable
fun MemoryScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoryViewModel,
    currentUser: FirebaseAuth?
){
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val state = viewModel.state.value

    val backgroundImage = if(isPortrait) state.theme.backgroundPortrait
    else state.theme.backgroundLandScape

    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()){
        Image( painter = painterResource(id = backgroundImage),
            contentDescription = "BackgroundImage",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())

        Row (
            modifier = Modifier.fillMaxSize()
        ){
            val rowRange = calculateRowRange(pairCount = state.pairCount, isPortrait = isPortrait)

            if(isPortrait){
                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 18.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    for (range in rowRange){
                        BuildCardRow(
                            range = range,
                            numberOfRows = rowRange.count(),
                            viewModel = viewModel,
                            modifier = Modifier.weight(1f),
                            isLastRow = rowRange.last() == range
                        )
                    }


                }
            }else{
                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 4.dp, bottom = 4.dp)
                        .weight(4f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    for (range in rowRange){
                        BuildCardRow(
                            range = range,
                            numberOfRows = rowRange.count(),
                            viewModel = viewModel,
                            modifier = Modifier.weight(1f),
                            isLastRow = rowRange.last() == range
                        )
                    }

                }
                Column (
                    modifier = modifier
                        .fillMaxSize(0.75f)
                        .padding(top = 4.dp, bottom = 4.dp)
                        .weight(4f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }
            }
        }
    }
    if (state.pairCount == state.pairMatched){
        val numberOfCards = state.pairCount * 2
        val numberOfClicks = state.clickCount
        val score = numberOfCards.toFloat() / numberOfClicks

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Score: $score",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = { context.startActivity(Intent(context, MainActivity::class.java))},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Finish")
                    }
                }
            }
        }
        currentUser?.let {
            viewModel.saveGameResult(MemoryGameDataClass(UUID.randomUUID().toString(), numberOfCards, numberOfClicks, score))
        }
    }
}

@Composable
fun BuildCardRow(
    modifier: Modifier = Modifier,
    range: IntRange,
    numberOfRows: Int,
    viewModel: MemoryViewModel,
    isLastRow:Boolean = false
){
    val state = viewModel.state.value
    Box(modifier = modifier){
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize())
        {
            for (cardsId in range){
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()){
                    MemoryGameCard(card = state.cards[cardsId],
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxSize(),
                        state = state,
                        onCLick = {viewModel.onEvent(MemoryEvent.CardClick(cardsId))} )
                }

            }
            val modulus = (state.pairCount * 2)% numberOfRows
            if (isLastRow && modulus!=0){
                val fillCardNumber = numberOfRows - modulus
                val extraCards = 1.. fillCardNumber
                for (missingCards in extraCards){
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxSize())
                }
            }
        }

    }
}
