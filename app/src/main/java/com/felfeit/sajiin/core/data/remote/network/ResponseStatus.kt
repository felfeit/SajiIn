package com.felfeit.sajiin.core.data.remote.network

sealed class ResponseStatus<out T> {
    data class Success<T>(val data: T) : ResponseStatus<T>()
    data class Error(val message: String) : ResponseStatus<Nothing>()
    data object Empty : ResponseStatus<Nothing>()
}