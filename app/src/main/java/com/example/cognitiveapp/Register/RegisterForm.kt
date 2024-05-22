package com.example.cognitiveapp.Register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitiveapp.LogIn.Credentials
import com.example.cognitiveapp.LogIn.LogInActivity
import com.example.cognitiveapp.LogIn.LogInForm
import com.example.cognitiveapp.LogIn.checkCredentials
import com.example.cognitiveapp.MainActivity
import com.example.cognitiveapp.ui.theme.CognitiveAppTheme

@Composable
fun RegisterForm() {

    var credentials by remember {
        mutableStateOf(RegisterCredentials())
    }
    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        append("Already have an account? ")
        withStyle(style = SpanStyle(color = Color.Blue)) {
            append("Sign In")
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
                text = "Create an account",
                style = TextStyle(fontSize = 30.sp),
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "Register and start your tests",
                style = TextStyle(fontSize = 15.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = 40.dp)
            )
            Row {
                NameField(
                    value = credentials.name,
                    onChange = { data -> credentials = credentials.copy(name = data)}
                )
                Spacer(modifier = Modifier.width(20.dp))
                SurnameField(
                    value = credentials.surname,
                    onChange = {data -> credentials = credentials.copy(surname = data)}
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            EmailField(
                value = credentials.email,
                onChange = {data -> credentials = credentials.copy(email = data)}
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data) },
                submit = {checkCredentials(credentials, context) }
            )
            Spacer(modifier = Modifier.height(20.dp))
            RepeatPasswordField(
                value = credentials.repeatPassword,
                onChange = { data -> credentials = credentials.copy(repeatPassword = data) },
                submit = {checkCredentials(credentials, context) }
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
                Text(text = "SIGN UP", style = TextStyle(Color.White))
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = annotatedText,
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(context, LogInActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }


    }
}

fun checkCredentials(credentials: RegisterCredentials, context: Context) {
    if (credentials.isNotEmpty() && credentials.password == credentials.repeatPassword){
        context.startActivity(Intent(context, MainActivity::class.java))
        Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show()
        (context as Activity).finish()
    } else {
        Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
    }

}

data class RegisterCredentials(
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",
    var repeatPassword: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()
    }
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NameField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Name",
    placeholder: String = " "
) {
    val focusManager = LocalFocusManager.current

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = null
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier
            .width(140.dp)
            .border(
                width = 1.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
            .height(50.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Left) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White
        ),

        )

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SurnameField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Surname",
    placeholder: String = " "
) {
    val focusManager = LocalFocusManager.current

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = null
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
            .height(50.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White
        ),

        )

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EmailField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Email",
    placeholder: String = "Enter your Email"
) {
    val focusManager = LocalFocusManager.current

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Email,
            contentDescription = null
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
            .height(50.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White
        ),

        )

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password"
) {
    val focusManager = LocalFocusManager.current

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = null
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
            .height(50.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation =PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White
        ),

        )

}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RepeatPasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Repeat Password",
    placeholder: String = "Repeat your Password"
) {
    val focusManager = LocalFocusManager.current

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = null
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
            .height(50.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White
        ),

        )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterFormPreview() {
    CognitiveAppTheme {
        RegisterForm()
    }
}