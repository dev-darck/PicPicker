package com.project.unsplash_api.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.util.concurrent.TimeUnit

object NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val cacheHeader = response.header("CACHE_HEADER")

        if (cacheHeader == null || cacheHeader.contains("no-cache")
            || cacheHeader.contains("max-stale=0")
        ) {

            val cacheControl = CacheControl.Builder()
                .maxAge(15, TimeUnit.MINUTES)
                .build()

            val cacheRequest = request.newBuilder()
                .removeHeader("Pragma")
                .cacheControl(cacheControl)
                .build()

            return chain.proceed(cacheRequest)
        } else {
            return response
        }
    }
}

object OfflineInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: ConnectException) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(1, TimeUnit.DAYS)
                .build()

            val offlineRequest = chain.request().newBuilder()
                .removeHeader("Pragma")
                .cacheControl(cacheControl)
                .build()
            chain.proceed(offlineRequest)
        }
    }
}