package com.example.cognitiveapp.TestTwo.Game

import androidx.compose.ui.graphics.Color
import com.example.cognitiveapp.R

interface cardInterface {

    val backgroundPortrait: Int
    val backgroundLandScape:Int
    val cardBack: Int
    val cardBaseColor: Color
    val textColor: Color
    val cardFrontBaseColor: Color
    val matchedOutlineColor: Color
    val imageMap: Map<Int, Int>

    fun getImageResourceForNumber(number: Int): Int?
}

class Theme(
    override val backgroundPortrait: Int = R.drawable.white,
    override val backgroundLandScape: Int = R.drawable.white,
    override val cardBack: Int = R.drawable.questionmark,
    override val cardBaseColor: Color = Color.White,
    override val textColor: Color = Color.White,
    override val cardFrontBaseColor: Color = Color.White,
    override val matchedOutlineColor: Color = Color.Green,
    override val imageMap: Map<Int, Int> = mapOf(
        1 to R.drawable.rabbit,
        2 to R.drawable.parrot,
        3 to R.drawable.dog,
        4 to R.drawable.cat,
        5 to R.drawable.fretka,
        6 to R.drawable.horse
    ),

):cardInterface{
    override fun getImageResourceForNumber(number: Int): Int? {
        return imageMap[number]
    }

}