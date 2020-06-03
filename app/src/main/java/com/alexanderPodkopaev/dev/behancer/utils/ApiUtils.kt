package com.alexanderPodkopaev.dev.behancer.utils

import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.data.api.ApiKeyInterceptor
import com.alexanderPodkopaev.dev.behancer.data.api.BehanceApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*


object ApiUtils {
    val NETWORK_EXCEPTIONS = listOf<Class<*>>(
            UnknownHostException::class.java,
            SocketTimeoutException::class.java,
            ConnectException::class.java
    )
}