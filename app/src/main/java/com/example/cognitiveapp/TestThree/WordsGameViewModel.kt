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


class WordsGameViewModel : ViewModel() {

    private val _words = MutableLiveData<List<String>>()

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    val words: LiveData<List<String>> get() = _words

    private val _userInput = MutableLiveData<List<String>>()
    val userInput: LiveData<List<String>> get() = _userInput

    private val _results = MutableLiveData<List<Boolean>>()
    val results: LiveData<List<Boolean>> get() = _results

    private val _score = MutableLiveData<Pair<Int, Int>>()
    val score: LiveData<Pair<Int, Int>> get() = _score

    private val _showScore = MutableLiveData<Boolean>()
    val showScore: LiveData<Boolean> get() = _showScore

    private val theme: WordGameInterface = ThemeWordsGame()

    private val _navigateToAnswerForm = MutableLiveData<Boolean>()
    val navigateToAnswerForm: LiveData<Boolean> get() = _navigateToAnswerForm
    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long> get() = _remainingTime

    private val _isButtonEnabled = mutableStateOf(true)
    val isButtonEnabled: State<Boolean> get() = _isButtonEnabled

    private var countDownTimer: CountDownTimer? = null
    private val countdownDurationMillis: Long = 45 * 1000 // 45 seconds

    init {
        generateWords()
        startCountdown()
        _showScore.value = false
    }

    private fun generateWords() {
        val words = WordGameState.generateWordsArray(5).map { card ->
            theme.getWordResourceForNumber(card.value)?.let {
                theme.wordAnimals[it] ?: theme.wordFruits[it] ?: theme.wordColors[it] ?:
                theme.wordVegetables[it] ?: theme.wordPlants[it]
            } ?: ""
        }
        _words.value = words
    }

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

    fun updateUserInput(index: Int, word: String) {
        val updatedList = _userInput.value?.toMutableList() ?: mutableListOf("", "", "", "", "")
        updatedList[index] = word
        _userInput.value = updatedList
    }

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


    fun onAnswerFormNavigated() {
        _navigateToAnswerForm.value = false
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }

}
