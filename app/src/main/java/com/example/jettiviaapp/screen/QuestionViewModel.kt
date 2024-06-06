package com.example.jettiviaapp.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettiviaapp.data.api.Loading
import com.example.jettiviaapp.data.api.Result
import com.example.jettiviaapp.data.model.QuestionItem
import com.example.jettiviaapp.data.model.Questions
import com.example.jettiviaapp.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    val data: MutableState<Result<Questions>?> = mutableStateOf(null)

    init {
        getaAllQuestions()
    }

    private fun getaAllQuestions() {
        viewModelScope.launch {
            data.value = Loading()
            data.value =  repository.getAllQuestions()
        }
    }
}