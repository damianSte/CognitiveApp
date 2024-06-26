package com.example.cognitiveapp.TestOne


/**
 * Represents the state of the "Odd One Out" test, including the cards and theme.
 *
 * @property cards Array of [OddOneOutCard] representing the cards in the test.
 * @property theme [OddOneOutCardInterface] representing the theme or configuration of the test.
 */
class OddOneOutState(
    val cards: Array<OddOneOutCard> = generateCardArray(4),
    val theme: OddOneOutCardInterface = ThemeOddOneOut()
) {
    companion object {


        /**
         * Generates an array of [OddOneOutCard] based on a specified size.
         *
         * @param size The size of the array to generate.
         * @return Array of [OddOneOutCard] with randomly assigned values.
         */
        fun generateCardArray(size: Int): Array<OddOneOutCard> {
            val correctValues = (1..6).shuffled().take(3).toMutableList()
            val incorrectValues = listOf(7, 8, 9, 10).shuffled().take(1)
            val allValues = (correctValues + incorrectValues).shuffled()

            return Array(size) { index -> OddOneOutCard(allValues[index], false) }
        }
    }
}
