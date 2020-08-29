package com.intsoftdev.nreclient.domain

import com.intsoftdev.nreclient.domain.ApiError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toApiError(): ApiError {
    return when (this) {
        is HttpException -> {
            when (this.code()) {
                in 400..499 -> ApiError.ClientError(
                    this.code(),
                    this.localizedMessage ?: "Oops something went wrong..."
                )
                else -> ApiError.ServerError(
                    this.code(),
                    this.localizedMessage ?: "Oops something went wrong..."
                )
            }
        }
        is IOException -> {
            ApiError.NetworkError(this.localizedMessage ?: "Oops something went wrong...")
        }
        else -> {
            ApiError.ServerError(500, this.toString())
        }
    }
}
