package com.example.cognitiveapp.TestOne

sealed class OddOneOutEvent {
        data class WordClick(val wordId: Int): OddOneOutEvent()
        object ResetGame: OddOneOutEvent()

}