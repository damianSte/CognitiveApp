package com.example.cognitiveapp.Register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme
import com.google.firebase.FirebaseApp

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Set the content of the activity
        setContent {
            CognitiveAppTheme {
                RegisterForm()
            }
        }
    }
}