package com.example.cognitiveapp.TestTwo.Game


fun generateCardArray(matches: Int): Array<MemoryCard>{

    val singles = 1..matches
    val doubles = singles+singles

    return doubles.shuffled().map { MemoryCard(it) }.toTypedArray()
}