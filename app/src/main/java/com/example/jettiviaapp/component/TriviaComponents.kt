package com.example.jettiviaapp.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettiviaapp.domain.Answer
import com.example.jettiviaapp.utils.AppColors
import com.example.jettiviaapp.utils.TriviaAction

@Composable
fun LoadingProgress(color: Color = AppColors.mDarkPurple) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color),
        contentAlignment = Alignment.Center) {

        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}

@Preview
@Composable
fun ShowProgress(score: Int = 10) {
    val gradient = Brush.linearGradient(listOf(
        AppColors.mLightRed, AppColors.mLightMagenta
    ))

    val progress = (score * 0.005f)


    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            border = BorderStroke(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(AppColors.mLightPurple, AppColors.mLightPurple)
                )
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            shape = RoundedCornerShape(
                topEndPercent = 50,
                topStartPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {

        Button(
            contentPadding = PaddingValues(1.dp),
            enabled = false,
            elevation = null,
            modifier = Modifier
                .fillMaxWidth(progress)
                .background(brush = gradient),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent),
            onClick = {}) {
            
            Text(text = "${score * 10}",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect =
                       PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)) {
        drawLine(color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun AnswerComponent(
    answer: Answer
) {
    AnswerContainer(index = answer.index, updateAnswer = answer.updateAnswer) {

        AnswerRadioButton(answer = answer)

        AnswerText(answer = answer)
    }
}

@Composable
fun AnswerContainer(
    index: Int,
    updateAnswer: TriviaAction,
    content: @Composable () -> Unit) {

    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple)
            ),
            shape = RoundedCornerShape(15.dp)
        )
        .clickable {
            updateAnswer(index)
        }
        .clip(
            shape = RoundedCornerShape(
                topEndPercent = 50,
                topStartPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {

        content()
    }
}

@Composable
fun AnswerRadioButton(answer: Answer) {
    answer.run {
        RadioButton(
            selected = (answerIndexState == index), onClick = {
                updateAnswer(index)
            },
            modifier = Modifier
                .padding(start = 16.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = if(answerState == true
                    && index == answerIndexState) {
                    Color.Green.copy(alpha = 0.5f)
                } else {
                    Color.Red.copy(alpha = 0.5f)
                }
            ))
    }
}

@Composable
fun AnswerText(answer: Answer) {
    val annotatedString = buildAnnotatedString {
        answer.run {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Light,
                    color = if(answerState == true
                        && index == answerIndexState) {
                        Color.Green
                    } else if(answerState == false
                        && index == answerIndexState) {
                        Color.Red
                    } else {
                        AppColors.mOffWhite
                    },
                    fontSize = 17.sp
                ),
            ) {
                append(answer.answer)
            }
        }
    }

    Text(text = annotatedString)
}