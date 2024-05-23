package com.example.cognitiveapp.TestThree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

class TestThreeActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveAppTheme {
                TestThreeForm()
            }
        }
    }
}