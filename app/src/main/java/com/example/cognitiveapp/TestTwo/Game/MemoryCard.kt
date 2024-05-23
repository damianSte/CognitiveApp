package com.example.cognitiveapp.TestTwo.Game

class MemoryCard (
    var value:Int,
    var isBackDisplayed: Boolean = true,
    var machFound: Boolean = false,
){
    fun flipCard(){
        isBackDisplayed =!isBackDisplayed
    }
}