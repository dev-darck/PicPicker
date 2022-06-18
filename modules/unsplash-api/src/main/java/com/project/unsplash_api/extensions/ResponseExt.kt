package com.project.unsplash_api.extensions

import com.project.unsplash_api.ResultWrapper
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

private const val TAG = "UnsplashApi"

internal fun <T : Any> Response<T>.result(): T {
    if (!isSuccessful) throw IOException("response bad ${errorBody()?.string()}")
    return body() ?: throw IOException("response bad ${errorBody()?.string()}")
}

internal fun <T : Any> Response<T>.safeCall(): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(result())
    } catch (e: Throwable) {
        errorState(e)
    }
}

private fun <T> errorState(throwable: Throwable): ResultWrapper<T> {
    Timber.tag(TAG).i(throwable)
    return when (throwable) {
        is java.io.IOException -> ResultWrapper.NetworkError
        is HttpException -> {
            val code = throwable.code()
            ResultWrapper.GenericError(code, throwable)
        }

        else -> {
            ResultWrapper.GenericError(null, null)
        }
    }
}