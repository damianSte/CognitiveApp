package com.example.cognitiveapp.firebase


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cognitiveapp.TestOne.OddOneOutCard
import com.example.cognitiveapp.TestOne.OddOneOutState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class OddOneOutViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    var state = OddOneOutState()
    var currentRound = 1
    val totalRounds = 5
    var correctAnswers = 0

    fun resetGame() {
        currentRound = 1
        state = OddOneOutState()
        correctAnswers = 0
    }

    fun nextRound() {
        if (currentRound < totalRounds) {
            currentRound++
            state = OddOneOutState()
        }
    }

    fun saveGameResult() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.w(ContentValues.TAG, "No user is logged in.")
            return
        }

        val score = correctAnswers.toFloat() / totalRounds
        val gameData = OneOddOutDataClass(score = score)

        currentUser.uid.let { userId ->
            val userGamesRef = db.collection("users").document(userId).collection("OddOneOutGames")
            userGamesRef
                .add(gameData)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "Game result added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding game result", e)
                }
        }
    }

    fun handleWordClick(wordId: Int): Boolean {
        val correctAnswer = state.cards.firstOrNull { state.theme.incorrectWordMap[it.value] != null }?.value
        return if (correctAnswer == wordId) {
            state.cards.forEach { it.machFound = true }
            correctAnswers++
            true
        } else {
            false
        }
    }

    fun getCurrentCards(): Array<OddOneOutCard> {
        return state.cards
    }

    fun getImageResourceForCard(card: OddOneOutCard): Int? {
        return state.theme.getImageResourceForNumber(card.value)
    }

    fun getWordForNumber(number: Int): String? {
        return state.theme.correctWordMap[number] ?: state.theme.incorrectWordMap[number]
    }

    fun isGameComplete(): Boolean {
        return currentRound >= totalRounds
    }
}
