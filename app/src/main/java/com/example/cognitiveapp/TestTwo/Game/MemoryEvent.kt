package com.example.cognitiveapp.TestTwo.Game

sealed class MemoryEvent {
    data class CardClick(val cardId: Int): MemoryEvent()
    //object AddPair: MemoryEvent()
    //object ReducePair: MemoryEvent()
    object ResetGame: MemoryEvent()
}