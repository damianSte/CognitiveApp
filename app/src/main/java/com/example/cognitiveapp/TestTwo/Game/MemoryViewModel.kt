package com.example.cognitiveapp.TestTwo.Game

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import androidx.lifecycle.ViewModel
import com.example.cognitiveapp.firebase.MemoryGameDataClass
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemoryViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private val _state = mutableStateOf(MemoryState())
    val state: State<MemoryState> = _state
    private var delayedCompareJob: Job? = null

    init {
        initialFlip()
    }

    fun saveGameResult(result: MemoryGameDataClass) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.w(TAG, "No user is logged in.")
            return
        }

        currentUser.uid.let { userId ->
            val userGamesRef = db.collection("users").document(userId).collection("MemoryCardGames")
            userGamesRef
                .add(result)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Game result added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding game result", e)
                }
        }
    }


    fun onEvent(event: MemoryEvent){

        when(event){
            is MemoryEvent.CardClick -> {
                onCardClick(event.cardId)
            }
            MemoryEvent.ResetGame -> {
                resetGame()
            }
        }
    }

    private fun increaseClickCount(){
        _state.value = _state.value.copy(
            clickCount = _state.value.clickCount + 1
        )
    }

    private fun resetGame(){
        _state.value = MemoryState(
            cards = generateCardArray(_state.value.pairCount),
            pairCount = _state.value.pairCount,
            theme = _state.value.theme
        )
        initialFlip()
    }

    private fun initialFlip() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                flipAllCards(showImages = true)
            }
            delay(10000)
            withContext(Dispatchers.Main) {
                flipAllCards(showImages = false)
            }
        }
    }

    private fun flipAllCards(showImages: Boolean) {
        val cards = _state.value.cards.copyOf()
        for (card in cards) {
            card.isBackDisplayed = !showImages
        }
        _state.value = _state.value.copy(cards = cards)
    }

    private fun onCardClick(id: Int){
        cancelPreviousJob()
        increaseClickCount()
        val cards = _state.value.cards

        if(cards[id].isBackDisplayed){
            delayedCompareJob = viewModelScope.launch ( Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    flip(id)
                }

                val firstIndex = _state.value.card1
                val secondIndex = _state.value.card2
                val bothCardsAreNotNull = firstIndex != null && secondIndex != null
                val cardsMatchSkipDelay = if (bothCardsAreNotNull) cards[firstIndex!!].value == cards[secondIndex!!].value
                else false
                if (!cardsMatchSkipDelay) {
                    delay(2000)
                }
                withContext(Dispatchers.Main) {
                    compareValues(firstIndex, secondIndex)
                }
            }
        }
    }

    private fun flip(id: Int){
        val cards = _state.value.cards.copyOf()
        cards[id].flipCard()
        val card2 = _state.value.card1
        _state.value = state.value.copy(
            card1 = id,
            card2 = card2,
            cards = cards
        )
    }

    private fun compareValues(first: Int?, second: Int?){
        val cards = _state.value.cards.copyOf()
        if (second != null && first != null) {
            val card1 = cards[first]
            val card2 = cards[second]
            if (card1.value != card2.value) {
                cards[first].flipCard()
                cards[second].flipCard()
            } else {
                cards[first].machFound = true
                cards[second].machFound = true
                _state.value = state.value.copy(
                    cards = cards,
                    pairMatched = _state.value.pairMatched + 1
                )
            }
        }

        resetCompareCards()
    }

    private fun resetCompareCards(){
        if (_state.value.card2 != null) {
            _state.value = _state.value.copy(card1 = null, card2 = null)
        }
    }

    private fun cancelPreviousJob(){
        val firstIndex = _state.value.card1
        val secondIndex = _state.value.card2

        if (delayedCompareJob != null) {
            delayedCompareJob?.cancel()
            compareValues(firstIndex, secondIndex)
        }
    }
}
