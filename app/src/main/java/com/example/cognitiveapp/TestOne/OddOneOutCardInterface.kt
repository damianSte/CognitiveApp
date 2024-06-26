package com.example.cognitiveapp.TestOne
import com.example.cognitiveapp.R

/**
 * Interface defining operations related to the Odd One Out test cards.
 */
interface OddOneOutCardInterface{

    /**
     * Map containing correct image resources associated with their respective numbers.
     */
    val correctImageMap: Map<Int, Int>

    /**
     * Map containing incorrect image resources associated with their respective numbers.
     */
    val incorrectImageMap: Map<Int, Int>

    /**
     * Map containing correct word descriptions associated with their respective numbers.
     */
    val correctWordMap : Map<Int, String>
    /**
     * Map containing incorrect word descriptions associated with their respective numbers.
     */
    val incorrectWordMap : Map<Int, String>

    /**
     * Retrieves the image resource ID for the given number.
     *
     * @param number The number associated with the image resource.
     * @return The resource ID of the image, or null if no resource is found for the number.
     */
    fun getImageResourceForNumber(number: Int): Int?
}


/**
 * Implementation of OddOneOutCardInterface that provides specific maps for the "Odd One Out" theme.
 *
 * @param correctWordMap Map of correct word descriptions associated with their respective numbers.
 * @param correctImageMap Map of correct image resources associated with their respective numbers.
 * @param incorrectImageMap Map of incorrect image resources associated with their respective numbers.
 * @param incorrectWordMap Map of incorrect word descriptions associated with their respective numbers.
 */
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


    /**
     * Retrieves the image resource ID for the given number.
     *
     * @param number The number associated with the image resource.
     * @return The resource ID of the image, or null if no resource is found for the number.
     */
    override fun getImageResourceForNumber(number: Int): Int? {
        return correctImageMap[number] ?: incorrectImageMap[number]
    }
}
