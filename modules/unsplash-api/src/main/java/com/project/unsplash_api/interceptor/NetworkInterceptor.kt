package com.project.unsplash_api.interceptor

import com.project.unsplash_api.BuildConfig
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

private const val ERROR_504 = 504
private const val ERROR_408 = 408

// перенести данную работу в @IntoSet и подключить через Hilt
object NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val cacheHeader = original.header("CACHE_HEADER")
        val builder = original.newBuilder()
        val accessKey = BuildConfig.UNSPLASH_ACCESS_KEY
        //Добавить проверку на Id user's
        builder.addHeader("Authorization", "Client-ID $accessKey")

        if (cacheHeader == null || cacheHeader.contains("no-cache")
            || cacheHeader.contains("max-stale=0")
        ) {

            val cacheControl = CacheControl.Builder()
                .maxAge(15, TimeUnit.MINUTES)
                .build()

            builder
                .removeHeader("Pragma")
                .cacheControl(cacheControl)

            return chain.safeRequest(builder.build())
        } else {
            return chain.safeRequest(builder.build())
        }
    }
}

object OfflineInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.safeRequest(chain.request())
        } catch (e: ConnectException) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(1, TimeUnit.DAYS)
                .build()

            val offlineRequest = chain.request().newBuilder()
                .removeHeader("Pragma")
                .cacheControl(cacheControl)
                .build()
            chain.safeRequest(offlineRequest)
        }
    }
}

private fun Interceptor.Chain.safeRequest(request: Request): Response {
    Timber.tag("RequestParam").i(
        "Url ${request.url} \n Headers ${request.headers} \n"
    )
    return try {
        proceed(request)
    } catch (e: Throwable) {
        Timber.i(e)
        when (e) {
            is SocketTimeoutException -> createResponse(request, ERROR_408, e.message.orEmpty(), "Socket timeout error")
            else -> createResponse(request, ERROR_504, e.message.orEmpty(), "")
        }
    }
}

private fun createResponse(
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