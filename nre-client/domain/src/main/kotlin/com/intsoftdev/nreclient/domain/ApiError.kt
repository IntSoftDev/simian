package com.intsoftdev.nreclient.domain

sealed class ApiError(val message: String) {
    class NetworkError(message: String): ApiError(message)
    class ServerError(val code: Int, message: String): ApiError(message)
    class ClientError(val code: Int, message: String): ApiError(message)
}