package com.example.depilationapp.data.util

sealed class Response<T> {
    object Loading : Response<Nothing>()

    data class Success<T>(
        val data: T
    ) : Response<T>()

    data class Failure(
        val e: Exception?
    ) : Response<Nothing>()
}