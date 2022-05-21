package com.project.unsplash_api

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: Any? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}