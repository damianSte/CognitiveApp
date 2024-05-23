package com.example.cognitiveapp.TestTwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.cognitiveapp.TestTwo.Game.MemoryViewModel
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

class TestTwoActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize()
                ) {
                    val viewModel: MemoryViewModel by viewModels()
                    MemoryScreen(viewModel = viewModel)

                }
            }
        }
    }
}