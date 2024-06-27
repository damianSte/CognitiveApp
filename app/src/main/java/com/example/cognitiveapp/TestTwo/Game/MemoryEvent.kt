package com.example.cognitiveapp.TestTwo.Game


/**
 * Sealed class representing events related to a memory game.
 */
sealed class MemoryEvent {

    /**
     * Represents a card click event.
     *
     * @property cardId The ID of the card that was clicked.
     */
    data class CardClick(val cardId: Int): MemoryEvent()
    //object AddPair: MemoryEvent()
    //object ReducePair: MemoryEvent()
    /**
     * Represents an event to reset the game.
     */
    object ResetGame: MemoryEvent()
}