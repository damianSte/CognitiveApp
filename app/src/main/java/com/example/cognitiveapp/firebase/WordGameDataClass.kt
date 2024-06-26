package com.example.cognitiveapp.firebase

import java.util.UUID


/**
 * Data class representing the word game data.
 *
 * @property gameId The unique identifier for the game, generated automatically. Default is a new UUID.
 * @property words The list of words provided in the game. Default is null.
 * @property userWords The list of words entered by the user. Default is null.
 * @property score The score achieved by the user. Default is 0.0.
 */
data class WordGameDataClass (

    val gameId: String? = generateUUID(),
    val words: List<String>? = null,
    val userWords: List<String>? = null,
    val score : Float? = 0.0f,
)

/**
 * Generates a unique identifier (UUID) as a string.
 *
 * @return A string representation of a newly generated UUID.
 */
private fun generateUUID() : String {
    return UUID.randomUUID().toString()
}