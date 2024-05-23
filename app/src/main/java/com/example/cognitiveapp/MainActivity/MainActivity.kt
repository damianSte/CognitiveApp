package com.example.cognitiveapp.MainActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cognitiveapp.TestOne.TestOneActivity
import com.example.cognitiveapp.TestThree.TestThreeActivity
import com.example.cognitiveapp.TestTwo.TestTwoActivity
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveAppTheme {
                MainActivityForm{testNumber ->
                    when(testNumber){
                        1 -> directToTestOne()
                        2 -> directToTestTwo()
                        3 -> directToTestThree()
                    }
                }
            }
        }
    }
    private fun directToTestOne(){
        val intent = Intent(this, TestOneActivity::class.java)
        startActivity(intent)
    }

    private fun directToTestTwo(){
        val intent = Intent(this, TestTwoActivity::class.java)
        startActivity(intent)
    }

    private fun directToTestThree(){
        val intent = Intent(this, TestThreeActivity::class.java)
        startActivity(intent)
    }
}

