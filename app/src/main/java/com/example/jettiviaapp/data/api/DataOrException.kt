package com.example.jettiviaapp.data.api

data class DataOrException<T, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)