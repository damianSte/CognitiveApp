package com.example.cognitiveapp.TestOne
import com.example.cognitiveapp.R

interface OddOneOutCardInterface{

    val correctImageMap: Map<Int, Int>
    val incorrectImageMap: Map<Int, Int>
    val correctWordMap : Map<Int, String>
    val incorrectWordMap : Map<Int, String>
    fun getImageResourceForNumber(number: Int): Int?
}

class ThemeOddOneOut(

    override val correctWordMap: Map<Int, String> = mapOf(
        1 to "octopus",
        2 to "parrot",
        3 to "dog",
        4 to "cat",
        5 to "frog",
        6 to "horse"
    ),
    override val correctImageMap: Map<Int, Int> = mapOf(
        1 to R.drawable.octopus,
        2 to R.drawable.parrot,
        3 to R.drawable.dog,
        4 to R.drawable.cat,
        5 to R.drawable.frog,
        6 to R.drawable.horse
    ),
    override val incorrectImageMap: Map<Int, Int> = mapOf(
        7 to R.drawable.apple,
        8 to R.drawable.banana,
        9 to R.drawable.chainsaw,
        10 to R.drawable.telephone
    ),
    override val incorrectWordMap: Map<Int, String> = mapOf(
        7 to "apple",
        8 to "banana",
        9 to "chainsaw",
        10 to "telephone"
    )

): OddOneOutCardInterface {
    override fun getImageResourceForNumber(number: Int): Int? {
        return correctImageMap[number] ?: incorrectImageMap[number]
    }
}
