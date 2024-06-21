package com.example.cognitiveapp.LogIn

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitiveapp.MainActivity.MainActivity
import com.example.cognitiveapp.R
import com.example.cognitiveapp.Register.RegisterActivity
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LogInForm() {

    var credentials by remember {
        mutableStateOf(Credentials())
    }
    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        append("Don't have an account? ")
        withStyle(style = SpanStyle(color = Color.Blue)) {
            append("Sign Up")
        }
    }

    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "Cognitive-App",
                style = TextStyle(fontSize = 40.sp),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            val image: Painter =
                painterResource(id = R.drawable.icon2)
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "Sign In",
                style = TextStyle(fontSize = 30.sp),
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )

            LogInField(
                value = credentials.login,
                onChange = { data -> credentials = credentials.copy(login = data) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(
                value = credentials.pwd,
                onChange = { data -> credentials = credentials.copy(pwd = data) },
                submit = { checkCredentials(credentials, context) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { checkCredentials(credentials, context) },
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )

            )
            {
                Text(text = "LogIn", style = TextStyle(Color.White))
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = annotatedText,
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(context, RegisterActivity::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            )
        }
    }
}

fun checkCredentials(credentials: Credentials, context: Context) {
    if (credentials.isNotEmpty()) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(credentials.login, credentials.pwd)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as Activity).finish()
                } else {
                    Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    } else {
        Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogInFormPreview() {
    CognitiveAppTheme {
        LogInForm()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogInFormPreviewDark() {
    CognitiveAppTheme(darkTheme = true) {
        LogInForm()
    }
}

