package com.project.unsplash_api.extensions

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber
import java.net.SocketTimeoutException

private const val ERROR_504 = 504
private const val ERROR_408 = 408

internal fun Interceptor.Chain.safeRequest(request: Request): Response {
    return try {
        proceed(request)
    } catch (e: Throwable) {
        Timber.i(e)
        when (e) {
            is SocketTimeoutException -> createResponse(
                request,
                ERROR_408,
                e.message.orEmpty(),
                "Socket timeout error"
            )
            else -> createResponse(request, ERROR_504, e.message.orEmpty(), "")
        }
    }
}

internal fun createResponse(
    request: Request,
    code: Int,
    body: String,
    msg: String,
): Response =
    Response.Builder().apply {
        message(msg)
        code(code)
        request(request)
        protocol(Protocol.HTTP_1_1)
        body(body.toResponseBody(null))
    }.build()