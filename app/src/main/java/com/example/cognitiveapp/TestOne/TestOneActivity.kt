package com.example.cognitiveapp.TestOne

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

class TestOneActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveAppTheme {
                TestOneForm()
            }
        }
    }
}