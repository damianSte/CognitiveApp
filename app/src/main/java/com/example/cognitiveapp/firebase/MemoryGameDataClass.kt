package com.example.cognitiveapp.firebase

/**
 * Data class representing the memory game data.
 *
 * @property gameId The unique identifier for the game. Default is an empty string.
 * @property numberOfClicks The number of clicks made by the player. Default is 0.
 * @property numberOfCards The total number of cards in the game. Default is 14.
 * @property score The score achieved by the player. Default is 0.0.
 */
data class MemoryGameDataClass (
    val gameId: String? = "",
    val numberOfClicks : Int? = 0,
    val numberOfCards: Int? = 14,
    val score : Float? = 0.0f,
)