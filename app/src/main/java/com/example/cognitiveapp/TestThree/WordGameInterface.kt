package com.example.cognitiveapp.TestThree

/**
 * Interface defining the structure for the word game, including categories of words and a method to get word resources.
 */
interface WordGameInterface {
    /**
     * Map of animal words with their corresponding numbers.
     */
    val wordAnimals: Map<Int, String>

    /**
     * Map of fruit words with their corresponding numbers.
     */
    val wordFruits: Map<Int, String>

    /**
     * Map of color words with their corresponding numbers.
     */
    val wordColors: Map<Int, String>

    /**
     * Map of vegetable words with their corresponding numbers.
     */
    val wordVegetables: Map<Int, String>

    /**
     * Map of plant words with their corresponding numbers.
     */
    val wordPlants: Map<Int, String>

    /**
     * Method to get the resource for a given number.
     *
     * @param number The number for which to get the resource.
     * @return The resource ID if found, otherwise null.
     */
    fun getWordResourceForNumber(number: Int): Int?
}

/**
 * Class implementing the WordGameInterface, providing predefined word mappings for different categories.
 */
class ThemeWordsGame(
    override val wordAnimals: Map<Int, String> = mapOf(
        1 to "Cat",
        2 to "Dog",
        3 to "Lion",
        4 to "Tiger",
        5 to "Elephant",
        6 to "Giraffe",
        7 to "Monkey",
        8 to "Zebra",
        9 to "Bear",
        10 to "Wolf"
    ),
    override val wordFruits: Map<Int, String> = mapOf(
        11 to "Apple",
        12 to "Banana",
        13 to "Cherry",
        14 to "Date",
        15 to "Grape",
        16 to "Kiwi",
        17 to "Lemon",
        18 to "Mango",
        19 to "Orange",
        20 to "Peach"
    ),
    override val wordColors: Map<Int, String> = mapOf(
        21 to "Red",
        22 to "Blue",
        23 to "Green",
        24 to "Yellow",
        25 to "Purple",
        26 to "Orange",
        27 to "Pink",
        28 to "Brown",
        29 to "Black",
        30 to "White"
    ),
    override val wordVegetables: Map<Int, String> = mapOf(
        31 to "Carrot",
        32 to "Broccoli",
        33 to "Spinach",
        34 to "Potato",
        35 to "Tomato",
        36 to "Cucumber",
        37 to "Pepper",
        38 to "Onion",
        39 to "Garlic",
        40 to "Pea"
    ),
    override val wordPlants: Map<Int, String> = mapOf(
        41 to "Rose",
        42 to "Tulip",
        43 to "Sunflower",
        44 to "Daisy",
        45 to "Lily",
        46 to "Orchid",
        47 to "Bamboo",
        48 to "Fern",
        49 to "Palm",
        50 to "Cactus"
    )
) : WordGameInterface {
    /**
     * Gets the resource for a given number by checking all word categories.
     *
     * @param number The number for which to get the resource.
     * @return The resource ID if found, otherwise null.
     */
    override fun getWordResourceForNumber(number: Int): Int? {
        val allWords = wordAnimals + wordFruits + wordColors + wordVegetables + wordPlants
        return if (allWords.containsKey(number)) number else null
    }
}
