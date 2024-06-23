package com.example.cognitiveapp.TestThree

class WordGameState (

    val words: Array<WordGameCard> = generateWordsArray(5),
    val theme: WordGameInterface = ThemeWordsGame()
) {
    companion object {
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