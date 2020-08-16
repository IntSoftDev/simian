package com.intsoftdev.nreclient.domain

sealed class ReturnState<T> {
    class Loading<T> : ReturnState<T>()
    data class Success<T>(val data: T) : ReturnState<T>()
    data class Error<T>(val throwable: Throwable) : ReturnState<T>()

    val isLoading: Boolean get() = this is Loading<T>
    val isSuccess: Boolean get() = this is Success<T>
    val isError: Boolean get() = this is Error<T>
}
