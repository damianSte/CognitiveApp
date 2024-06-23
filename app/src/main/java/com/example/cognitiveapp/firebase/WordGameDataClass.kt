package com.example.cognitiveapp.firebase

import java.util.UUID

data class WordGameDataClass (

    val gameId: String? = generateUUID(),
    val words: List<String>? = null,
    val userWords: List<String>? = null,
    val score : Float? = 0.0f,
)

private fun generateUUID() : String {
    return UUID.randomUUID().toString()
}