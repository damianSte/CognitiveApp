package com.example.cognitiveapp.TestThree

/**
 * Class representing the state of the word game, including the words to be used and the theme.
 *
 * @property words Array of WordGameCard representing the words for the game.
 * @property theme The theme of the word game implementing the WordGameInterface.
 */
class WordGameState (

    val words: Array<WordGameCard> = generateWordsArray(5),
    val theme: WordGameInterface = ThemeWordsGame()
) {
    companion object {

        /**
         * Generates an array of WordGameCard with a specified size, each containing a unique word.
         *
         * @param size The number of WordGameCards to generate.
         * @return Array of WordGameCard with randomly selected words from different categories.
         */
        fun generateWordsArray(size: Int): Array<WordGameCard> {
            val animals = (1..10).shuffled().take(1).toMutableList()
            val fruits = (11..20).shuffled().take(1).toMutableList()
            val colors = (21..30).shuffled().take(1).toMutableList()
            val vegetables = (31..40).shuffled().take(1).toMutableList()
            val plant = (41..50).shuffled().take(1).toMutableList()
            val allWords = (animals + fruits + colors + vegetables + plant).shuffled()
            return Array(size) { index -> WordGameCard(allWords[index]) }
        }
    }


}