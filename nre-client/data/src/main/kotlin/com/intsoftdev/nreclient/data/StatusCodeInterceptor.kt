package com.intsoftdev.nreclient.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class StatusCodeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        Log.d(javaClass.simpleName, "Status code: " + response.code)
        response.networkResponse?.code.let {
            Log.d(javaClass.simpleName, "Network code: $it")
        }
        response.cacheResponse?.code.let {
            Log.d(javaClass.simpleName, "Cache code: $it")
        }
        return response
    }
}