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


/**
 * Activity for the Test One game. Sets the content view to the TestOneForm composable and initializes the ViewModel.
 */
class TestOneActivity : ComponentActivity() {

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in [onSaveInstanceState]. Note: Otherwise it is null.
     */
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
