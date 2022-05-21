package com.project.unsplash_api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

private const val TAG = "UnsplashApi"

fun <T : Any> Response<ResponseBody>.result(classType: Class<T>): T {
    if (!isSuccessful) throw IOException("response bad ${errorBody()}")
    val res = body()?.string()
    return Gson().fromJson(res, classType)
}

fun <T : Any> Response<ResponseBody>.resultLitOf(classType: Class<T>): List<T> {
    if (!isSuccessful) throw IOException("response bad ${errorBody()}")
    val typeOfT = TypeToken.getParameterized(MutableList::class.java, classType).type
    val res = body()?.string() ?: ""
    return Gson().fromJson(res, typeOfT)
}

fun <T: Any> Response<ResponseBody>.safeCall(classType: Class<T>): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(result(classType))
    } catch (e: Throwable) {
        errorState(e)
    }
}

fun <T: Any> Response<ResponseBody>.safeListCall(classType: Class<T>): ResultWrapper<List<T>> {
    return try {
        ResultWrapper.Success(resultLitOf(classType))
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