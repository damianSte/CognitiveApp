
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitiveapp.MainActivity.MainActivity
import com.example.cognitiveapp.TestThree.WordsGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerTextForm(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Enter the corresponding word",
    wordNo: String,
    isCorrect: Boolean? = null
) {
    val focusManager = LocalFocusManager.current
    val borderColor = when (isCorrect) {
        true -> Color.Green
        false -> Color.Red
        else -> Color.Black
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = value,
            onValueChange = onChange,
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(30.dp)
                )
                .height(50.dp),
            label = { Text(wordNo) },
            placeholder = { Text(placeholder) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            visualTransformation = VisualTransformation.None,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White
            ),)
    }
}

@Composable
fun TestThreeAnswerForm(viewModel: WordsGameViewModel) {
    val userInput by viewModel.userInput.observeAsState(emptyList())
    val results by viewModel.results.observeAsState(emptyList())
    val score by viewModel.score.observeAsState(Pair(0, 0))
    val showScore by viewModel.showScore.observeAsState(false)
    val isButtonEnabled by viewModel.isButtonEnabled
    val context = LocalContext.current

    Column {
        Box(
            Modifier
                .padding(vertical = 10.dp)
                .weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Write the words from the last view in their corresponding fields.",
                    textAlign = TextAlign.Center
                )
            }
        }

        for (i in 0 until 5) {
            Box(
                Modifier
                    .padding(10.dp)
                    .weight(1f)
            ) {
                AnswerTextForm(
                    value = userInput.getOrElse(i) { "" },
                    onChange = { word -> viewModel.updateUserInput(i, word) },
                    wordNo = "Word ${i + 1}",
                    isCorrect = results.getOrNull(i)
                )
            }
        }

        Box(
            Modifier
                .padding(vertical = 20.dp)
                .weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.checkAnswers()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff87bba2),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxSize(),
                    enabled = isButtonEnabled
                ) {
                    Text(text = "Check Answers")
                }
            }
        }


        if (showScore) {
            Box(
                Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Score: ${score.first}/${score.second}",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = {
                            context.startActivity(Intent(context, MainActivity::class.java))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff87bba2)
                        )
                    ) {
                        Text("Finish")
                    }
                }
            }
        }
    }
}

