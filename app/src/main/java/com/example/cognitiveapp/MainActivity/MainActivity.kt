package com.example.cognitiveapp.MainActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cognitiveapp.TestOne.TestOneActivity
import com.example.cognitiveapp.TestThree.TestThreeActivity
import com.example.cognitiveapp.TestTwo.TestTwoActivity
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme


/**
 * MainActivity serves as the main entry point of the application.
 * It displays the main form and handles navigation to different test activities based on user selection.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is first created. This is where you should do all of your normal static set up.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied.
     */

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
    /**
     * Navigates to the TestOneActivity.
     */

    private fun directToTestOne(){
        val intent = Intent(this, TestOneActivity::class.java)
        startActivity(intent)
    }

    /**
     * Navigates to the TestTwoActivity.
     */
    private fun directToTestTwo(){
        val intent = Intent(this, TestTwoActivity::class.java)
        startActivity(intent)
    }

    /**
     * Navigates to the TestThreeActivity.
     */
    private fun directToTestThree(){
        val intent = Intent(this, TestThreeActivity::class.java)
        startActivity(intent)
    }

    /**
     * Navigates back to the MainActivity.
     */
    private fun directToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

