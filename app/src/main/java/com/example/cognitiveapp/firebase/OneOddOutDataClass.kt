package com.example.cognitiveapp.firebase

import java.util.UUID

data class OneOddOutDataClass (

    val gameId: String? = generateUUID(),
    val score : Float? = 0.0f,
)


private fun generateUUID () : String {
    return UUID.randomUUID().toString()
}