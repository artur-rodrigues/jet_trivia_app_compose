package com.example.jettiviaapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettiviaapp.component.AnswerComponent
import com.example.jettiviaapp.component.DrawDottedLine
import com.example.jettiviaapp.component.LoadingProgress
import com.example.jettiviaapp.component.ShowProgress
import com.example.jettiviaapp.data.api.Error
import com.example.jettiviaapp.data.api.Loading
import com.example.jettiviaapp.data.api.Success
import com.example.jettiviaapp.data.model.QuestionItem
import com.example.jettiviaapp.data.model.Questions
import com.example.jettiviaapp.domain.Answer
import com.example.jettiviaapp.utils.AppColors
import com.example.jettiviaapp.utils.TriviaAction

@Composable
fun Questions(viewModel: QuestionViewModel) {
    viewModel.data.value.run {
        when(this) {
            is Error -> {}
            is Loading -> LoadingProgress()
            is Success -> {
                FullContent(questions = this.data)
            }
            else -> Unit
        }
    }
}

@Composable
fun FullContent(questions: Questions) {
    val questionIndex = remember {
        mutableIntStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.mDarkPurple) {

        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {

            if (questionIndex.intValue >= 3) {
               ShowProgress(questionIndex.intValue)
            }

            QuestionTracker(questionIndex.intValue, questions.size)

            DrawDottedLine()

            val questionItem = questions[questionIndex.intValue]

            QuestionDisplay(questionItem) {
                ++questionIndex.intValue
            }
        }
    }
}

@Composable
fun QuestionDisplay(
    question: QuestionItem,
    onNextClicked: TriviaAction = {}
) {
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    val answerIndexState = remember(question) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: TriviaAction = remember(question) {
        {
            answerIndexState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    QuestionContent(question, answerIndexState.value, correctAnswerState.value, updateAnswer, onNextClicked)
}

@Composable
fun QuestionTracker(counter: Int, outOf: Int) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(style = SpanStyle(color = AppColors.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp)) {
                    append("Question $counter/")

                    withStyle(style = SpanStyle(fontSize = 14.sp)) {
                        append("$outOf")
                    }
                }
            }
        })
}

@Composable
fun QuestionContent(
    question: QuestionItem,
    answerIndex: Int?,
    answerState: Boolean?,
    updateAnswer: TriviaAction,
    onNextClicked: TriviaAction
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(text = question.question,
            modifier = Modifier
                .padding(6.dp)
                .align(alignment = Alignment.Start)
                .fillMaxHeight(0.3f),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.sp,
            color = AppColors.mOffWhite)

        question.choices.forEachIndexed { index, answerText ->
            AnswerComponent(Answer(index, answerText, answerIndex, answerState, updateAnswer))
        }

        Button(
            onClick = {
                answerIndex?.let {
                    onNextClicked(it)
                }
            },  modifier = Modifier
                .padding(3.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(34.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.mLightBlue
            ),
            enabled = answerIndex != null
        ) {
            Text(text = "Next",
                modifier = Modifier.padding(4.dp),
                color = AppColors.mOffWhite,
                fontSize = 17.sp)
        }
    }
}