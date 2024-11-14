package com.challenge.gamesearch.core.utils

// TODO: Why a sealed class? A sealed class provides a controlled inheritance. No inheritance
// outside of the Module or Package where it is located.
sealed class ResponseHandler<T> {
    data class Success<T>(val data: T): ResponseHandler<T>()
    data class Error<T>(val message: String): ResponseHandler<T>()
}