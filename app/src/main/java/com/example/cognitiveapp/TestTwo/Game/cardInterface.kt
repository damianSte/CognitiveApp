package com.example.cognitiveapp.TestTwo.Game

import androidx.compose.ui.graphics.Color
import com.example.cognitiveapp.R

/**
 * Interface defining properties and methods for a card in a game.
 */
interface cardInterface {

    /** Background resource for portrait mode */
    val backgroundPortrait: Int

    /** Background resource for landscape mode */
    val backgroundLandScape: Int

    /** Card back resource */
    val cardBack: Int

    /** Base color of the card */
    val cardBaseColor: Color

    /** Text color on the card */
    val textColor: Color

    /** Base color of the front side of the card */
    val cardFrontBaseColor: Color

    /** Color of the outline when cards are matched */
    val matchedOutlineColor: Color

    /** Map associating numbers with image resources */
    val imageMap: Map<Int, Int>

    /**
     * Retrieves the image resource associated with the given number.
     *
     * @param number The number corresponding to the image resource.
     * @return The image resource ID if found, otherwise null.
     */
    fun getImageResourceForNumber(number: Int): Int?
}

/**
 * Implementation of the cardInterface representing a theme for the game.
 *
 * @property backgroundPortrait Resource ID for portrait mode background
 * @property backgroundLandScape Resource ID for landscape mode background
 * @property cardBack Resource ID for card back image
 * @property cardBaseColor Base color of the card
 * @property textColor Text color on the card
 * @property cardFrontBaseColor Base color of the front side of the card
 * @property matchedOutlineColor Color of the outline when cards are matched
 * @property imageMap Map associating numbers with image resources
 */
class Theme(
    override val backgroundPortrait: Int = R.drawable.white,
    override val backgroundLandScape: Int = R.drawable.white,
    override val cardBack: Int = R.drawable.questionmark,
    override val cardBaseColor: Color = Color.White,
    override val textColor: Color = Color.White,
    override val cardFrontBaseColor: Color = Color.White,
    override val matchedOutlineColor: Color = Color.Green,
    override val imageMap: Map<Int, Int> = mapOf(
        1 to R.drawable.octopus,
        2 to R.drawable.parrot,
        3 to R.drawable.dog,
        4 to R.drawable.cat,
        5 to R.drawable.frog,
        6 to R.drawable.horse
    )
) : cardInterface {

    /**
     * Retrieves the image resource ID associated with the given number.
     *
     * @param number The number corresponding to the image resource.
     * @return The image resource ID if found, otherwise null.
     */
    override fun getImageResourceForNumber(number: Int): Int? {
        return imageMap[number]
    }

}
