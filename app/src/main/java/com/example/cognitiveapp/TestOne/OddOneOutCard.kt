package com.example.cognitiveapp.TestOne


/**
 * Represents a card used in the "Odd One Out" test.
 *
 * @property value The integer value associated with the card.
 * @property matchFound Boolean indicating if this card has been matched correctly.
 */
class OddOneOutCard(
    var value: Int,
    var machFound: Boolean = false
)