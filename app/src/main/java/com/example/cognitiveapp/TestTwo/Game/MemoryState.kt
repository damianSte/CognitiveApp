package com.example.cognitiveapp.TestTwo.Game


/**
 * Represents the current state of the memory game.
 *
 * @property cards The array of memory cards in the game.
 * @property card1 The ID of the first selected card, or null if no card is selected.
 * @property card2 The ID of the second selected card, or null if no second card is selected.
 * @property pairCount The total number of card pairs in the game.
 * @property pairMatched The number of card pairs that have been matched.
 * @property clickCount The total count of card clicks in the game.
 * @property theme The theme used for the game, implementing [cardInterface].
 */
data class MemoryState(
    val cards: Array<MemoryCard> = generateCardArray(6),
    val card1: Int? = null,
    val card2: Int? = null,
    val pairCount: Int = 6,
    val pairMatched: Int = 0,
    val clickCount: Int = 0,
    val theme: cardInterface = Theme()
)