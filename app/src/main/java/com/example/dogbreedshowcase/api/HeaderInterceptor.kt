package com.example.dogbreedshowcase.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Dog-Agent", "Dog-Breed-Sample")
            .build()
        return chain.proceed(request)
    }
}