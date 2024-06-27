package com.example.cognitiveapp.TestThree

import android.content.ContentValues
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cognitiveapp.firebase.WordGameDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.State


/**
 * ViewModel for the Words Game, handling the game's state, countdown timer, and user interactions.
 */
class WordsGameViewModel : ViewModel() {

    // LiveData for game words
    private val _words = MutableLiveData<List<String>>()
    val words: LiveData<List<String>> get() = _words

    // LiveData for user input
    private val _userInput = MutableLiveData<List<String>>()
    val userInput: LiveData<List<String>> get() = _userInput

    // LiveData for results of user answers
    private val _results = MutableLiveData<List<Boolean>>()
    val results: LiveData<List<Boolean>> get() = _results

    // LiveData for current score (correct answers vs total)
    private val _score = MutableLiveData<Pair<Int, Int>>()
    val score: LiveData<Pair<Int, Int>> get() = _score

    // LiveData to show/hide the score screen
    private val _showScore = MutableLiveData<Boolean>()
    val showScore: LiveData<Boolean> get() = _showScore

    // LiveData to navigate to answer form screen
    private val _navigateToAnswerForm = MutableLiveData<Boolean>()
    val navigateToAnswerForm: LiveData<Boolean> get() = _navigateToAnswerForm

    // LiveData for remaining time on countdown
    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long> get() = _remainingTime

    // State for button enable/disable state
    private val _isButtonEnabled = mutableStateOf(true)
    val isButtonEnabled: State<Boolean> get() = _isButtonEnabled

    // Firebase Firestore instance
    private val db = FirebaseFirestore.getInstance()

    // Firebase Authentication instance
    private val auth = FirebaseAuth.getInstance()

    // Theme for the game, implementing WordGameInterface
    private val theme: WordGameInterface = ThemeWordsGame()

    // Countdown timer object
    private var countDownTimer: CountDownTimer? = null

    // Countdown duration in milliseconds
    private val countdownDurationMillis: Long = 45 * 1000 // 45 seconds

    /**
     * Initializes the ViewModel, generates words, starts countdown, and hides score screen.
     */
    init {
        generateWords()
        startCountdown()
        _showScore.value = false
    }
    /**
     * Generates the words for the game and updates the LiveData.
     */

    private fun generateWords() {
        val words = WordGameState.generateWordsArray(5).map { card ->
            theme.getWordResourceForNumber(card.value)?.let {
                theme.wordAnimals[it] ?: theme.wordFruits[it] ?: theme.wordColors[it] ?:
                theme.wordVegetables[it] ?: theme.wordPlants[it]
            } ?: ""
        }
        _words.value = words
    }

    /**
     * Saves the word game data to Firebase Firestore.
     */
    fun saveWordGameData() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.w(ContentValues.TAG, "No user is logged in.")
            return
        }

        val correctAnswers = _words.value ?: emptyList()
        val userAnswers = _userInput.value ?: emptyList()

        val numCorrect = correctAnswers.zip(userAnswers)
            .count { (correct, user) -> correct.equals(user, ignoreCase = true) }

        val score = (numCorrect.toFloat() / correctAnswers.size) * 100 // Calculate score

        val gameData = WordGameDataClass(
            words = correctAnswers,
            userWords = userAnswers,
            score = score
        )

        currentUser.uid.let { userId ->
            val userGamesRef = db.collection("users").document(userId).collection("WordGames")
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
     * Starts the countdown timer for the game.
     */
    private fun startCountdown() {
        _remainingTime.value = countdownDurationMillis

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(countdownDurationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingTime.value = millisUntilFinished
            }

            override fun onFinish() {
                _navigateToAnswerForm.value = true
            }
        }.start()
    }

    /**
     * Updates the user input at the given index.
     *
     * @param index The index of the input field.
     * @param word The word entered by the user.
     */
    fun updateUserInput(index: Int, word: String) {
        val updatedList = _userInput.value?.toMutableList() ?: mutableListOf("", "", "", "", "")
        updatedList[index] = word
        _userInput.value = updatedList
    }

    /**
     * Checks the user's answers against the correct answers and updates the results and score.
     */
    fun checkAnswers() {
        val correctAnswers = _words.value ?: emptyList()
        val userAnswers = _userInput.value ?: emptyList()

        _results.value = userAnswers.mapIndexed { index, word ->
            word.equals(correctAnswers.getOrNull(index) ?: "", ignoreCase = true)
        }

        val numCorrect = _results.value?.count { it } ?: 0
        _score.value = Pair(numCorrect, correctAnswers.size)

        saveWordGameData()
        _showScore.value = true
        _isButtonEnabled.value = false
    }


    /**
     * Resets the navigation state after navigating to the answer form.
     */

    fun onAnswerFormNavigated() {
        _navigateToAnswerForm.value = false
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }

}
