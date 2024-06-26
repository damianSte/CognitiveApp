package com.example.cognitiveapp.firebase

import java.util.UUID

/**
 * Data class representing the "One Odd Out" game data.
 *
 * @property gameId The unique identifier for the game, generated automatically. Default is a new UUID.
 * @property score The score achieved by the player. Default is 0.0.
 */
data class OneOddOutDataClass (

    val gameId: String? = generateUUID(),
    val score : Float? = 0.0f,
)

/**
 * Generates a unique identifier (UUID) as a string.
 *
 * @return A string representation of a newly generated UUID.
 */
private fun generateUUID () : String {
    return UUID.randomUUID().toString()
}