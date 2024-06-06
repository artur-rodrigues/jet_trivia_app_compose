package com.example.jettiviaapp.data.api

import com.example.jettiviaapp.data.model.Questions
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions(): Questions
}