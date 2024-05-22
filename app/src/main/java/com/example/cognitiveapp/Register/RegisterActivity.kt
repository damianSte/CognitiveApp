package com.example.cognitiveapp.Register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cognitiveapp.LogIn.LogInForm
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveAppTheme {
                RegisterForm()
            }
        }
    }
}