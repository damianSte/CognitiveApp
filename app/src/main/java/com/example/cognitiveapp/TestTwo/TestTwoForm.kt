package com.example.cognitiveapp.TestTwo

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitiveapp.TestTwo.Game.MemoryCard
import com.example.cognitiveapp.TestTwo.Game.MemoryEvent
import com.example.cognitiveapp.TestTwo.Game.MemoryState
import com.example.cognitiveapp.TestTwo.Game.MemoryViewModel


@Composable
fun ResetGameButton(
    viewModel: MemoryViewModel,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = { viewModel.onEvent(MemoryEvent.ResetGame) },
        icon = Icons.Default.Cached,
        contentDescription = "Reset Game",
        tint = Color.Black,
        modifier = modifier
    )
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    tint: Color,
    modifier: Modifier = Modifier

) {
    Box(modifier = modifier) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                Color.Transparent
            )
        ) {
            Icon(
                imageVector = icon, contentDescription = contentDescription,
                tint = tint, modifier = Modifier.size(20.dp)
            )

        }
    }
}

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
    viewModel: MemoryViewModel
){
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val state = viewModel.state.value
    
    val backgroundImage = if(isPortrait) state.theme.backgroundPortrait
    else state.theme.backgroundLandScape
    
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
                    Row (
                        modifier = modifier
                            .weight(.5f)
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        ResetGameButton(viewModel = viewModel, modifier = Modifier.weight(.1f))


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
                    ResetGameButton(viewModel = viewModel, modifier = Modifier.weight(.1f))
                }
            }
        }
    }
    if(state.pairCount == state.pairMatched){
        Box(modifier = modifier.fillMaxSize(),
           contentAlignment = Alignment.Center
        ){
            Card(modifier = modifier
                .fillMaxWidth(0.7f)
                .border(
                    width = 4.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = state.theme.matchedOutlineColor
                ),
                colors = CardDefaults.cardColors(
                    containerColor = state.theme.cardFrontBaseColor
                )
            ) {
                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) 
                {
                 Text(
                     text = "You have won!\n Score: ${state.clickCount} clicks",
                     color = Color.Black,
                     fontSize = 30.sp,
                     lineHeight = 30.sp,
                     modifier= Modifier.padding(8.dp)
                 )

                }
                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    IconButton(
                        onClick = { viewModel.onEvent(MemoryEvent.ResetGame) },
                        icon = Icons.Default.Cached,
                        contentDescription = "Reset Game",
                        tint = Color.White,
                        modifier = modifier
                    )
                }

            }
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
