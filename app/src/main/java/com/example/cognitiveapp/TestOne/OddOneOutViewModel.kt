package com.example.cognitiveapp.firebase


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cognitiveapp.TestOne.OddOneOutCard
import com.example.cognitiveapp.TestOne.OddOneOutState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * ViewModel for the Odd One Out game. Handles game state, user interactions, and saving game results to Firebase.
 */
class OddOneOutViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    var state = OddOneOutState()
    var currentRound = 1
    val totalRounds = 5
    var correctAnswers = 0

    /**
     * Resets the game state to the initial conditions.
     */
    fun resetGame() {
        currentRound = 1
        state = OddOneOutState()
        correctAnswers = 0
    }

    /**
     * Proceeds to the next round of the game if the current round is less than totalRounds.
     */
    fun nextRound() {
        if (currentRound < totalRounds) {
            currentRound++
            state = OddOneOutState()
        }
    }

    /**
     * Saves the game result to the Firebase Firestore under the current user's document.
     */
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

    /**
     * Handles a word click event, checks if the clicked word is the correct answer, and updates the game state accordingly.
     * @param wordId The ID of the clicked word.
     * @return True if the clicked word is the correct answer, false otherwise.
     */
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

    /**
     * Gets the current cards in the game.
     * @return An array of OddOneOutCard representing the current game cards.
     */
    fun getCurrentCards(): Array<OddOneOutCard> {
        return state.cards
    }

    /**
     * Gets the image resource ID for a given card.
     * @param card The OddOneOutCard to get the image resource for.
     * @return The image resource ID, or null if not found.
     */
    fun getImageResourceForCard(card: OddOneOutCard): Int? {
        return state.theme.getImageResourceForNumber(card.value)
    }

    /**
     * Gets the word associated with a given number.
     * @param number The number to get the word for.
     * @return The word associated with the number, or null if not found.
     */
    fun getWordForNumber(number: Int): String? {
        return state.theme.correctWordMap[number] ?: state.theme.incorrectWordMap[number]
    }

    /**
     * Checks if the game is complete.
     * @return True if the current round is greater than or equal to totalRounds, false otherwise.
     */
    fun isGameComplete(): Boolean {
        return currentRound >= totalRounds
    }
}
