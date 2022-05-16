package com.project.unsplash_api.interceptor

import com.project.unsplash_api.BuildConfig
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.util.concurrent.TimeUnit

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

            return chain.proceed(builder.build())
        } else {
            return chain.proceed(builder.build())
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