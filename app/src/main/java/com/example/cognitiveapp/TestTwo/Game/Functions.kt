package com.example.cognitiveapp.TestTwo.Game

/**
 * Generates an array of MemoryCard objects with pairs of cards based on the specified number of matches.
 *
 * @param matches The number of pairs of cards to generate.
 * @return An array of MemoryCard objects representing the generated cards.
 */
fun generateCardArray(matches: Int): Array<MemoryCard> {
    // Create a range of numbers from 1 to `matches`
    val singles = 1..matches
    // Duplicate the range to create pairs
    val doubles = singles + singles

    // Shuffle the list of doubled numbers and map each to a MemoryCard object
    return doubles.shuffled().map { MemoryCard(it) }.toTypedArray()
}
