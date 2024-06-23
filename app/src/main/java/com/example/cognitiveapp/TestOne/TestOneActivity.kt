package com.example.cognitiveapp.TestOne

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import com.example.cognitiveapp.MainActivity.MainActivity
import com.example.cognitiveapp.firebase.OddOneOutViewModel
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

class TestOneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveAppTheme {
                val viewModel: OddOneOutViewModel by viewModels()
                TestOneForm(viewModel = viewModel)

            }
        }
    }
}
