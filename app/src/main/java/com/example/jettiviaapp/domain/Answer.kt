package com.example.jettiviaapp.domain

import com.example.jettiviaapp.utils.TriviaAction

class Answer(
    val index: Int = -1,
    val answer: String = "Answer",
    val answerIndexState: Int? = null,
    val answerState: Boolean? = null,
    val updateAnswer: TriviaAction = {}
)