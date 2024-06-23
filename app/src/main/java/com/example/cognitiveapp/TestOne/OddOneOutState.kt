package com.example.cognitiveapp.TestOne

class OddOneOutState(
    val cards: Array<OddOneOutCard> = generateCardArray(4),
    val theme: OddOneOutCardInterface = ThemeOddOneOut()
) {
    companion object {
        fun generateCardArray(size: Int): Array<OddOneOutCard> {
            val correctValues = (1..6).shuffled().take(3).toMutableList()
            val incorrectValues = listOf(7, 8, 9, 10).shuffled().take(1)
            val allValues = (correctValues + incorrectValues).shuffled()

            return Array(size) { index -> OddOneOutCard(allValues[index], false) }
        }
    }
}
