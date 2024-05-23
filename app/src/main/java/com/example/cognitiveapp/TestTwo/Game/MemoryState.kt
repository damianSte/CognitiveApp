package com.example.cognitiveapp.TestTwo.Game

data class MemoryState(
    val cards: Array<MemoryCard> = generateCardArray(6),
    val card1: Int? = null,
    val card2: Int? = null,
    val pairCount: Int = 6,
    val pairMatched: Int = 0,
    val clickCount: Int = 0,
    val theme: cardInterface = Theme()
)