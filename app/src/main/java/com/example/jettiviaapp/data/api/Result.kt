package com.example.jettiviaapp.data.api

sealed class Result<T>
class Success<T>(val data: T) : Result<T>()
class Loading<T> : Result<T>()
class Error<T>(val error: Throwable) : Result<T>()