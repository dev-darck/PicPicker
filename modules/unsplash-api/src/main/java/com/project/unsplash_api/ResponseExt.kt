package com.project.unsplash_api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Response

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