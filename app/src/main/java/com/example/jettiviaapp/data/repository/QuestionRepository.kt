package com.example.jettiviaapp.data.repository

import com.example.jettiviaapp.data.api.Error
import com.example.jettiviaapp.data.api.QuestionApi
import com.example.jettiviaapp.data.api.Result
import com.example.jettiviaapp.data.api.Success
import com.example.jettiviaapp.data.model.Questions
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val questionApi: QuestionApi
) {

    suspend fun getAllQuestions(): Result<Questions> {
        return try {
            Success(questionApi.getAllQuestions())
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e)
        }
    }
}