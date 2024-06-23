package com.example.cognitiveapp.TestThree

import TestThreeAnswerForm
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

class TestThreeActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveAppTheme {
                val viewModel: WordsGameViewModel = viewModel()
                val navController = rememberNavController()
                AppNavHost(navController, viewModel)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, viewModel: WordsGameViewModel) {
    NavHost(navController = navController, startDestination = "TestThreeForm") {
        composable("TestThreeForm") {
            TestThreeForm(viewModel = viewModel, navController = navController)
        }
        composable("TestThreeAnswerForm") {
            TestThreeAnswerForm(viewModel = viewModel)
        }
    }
}