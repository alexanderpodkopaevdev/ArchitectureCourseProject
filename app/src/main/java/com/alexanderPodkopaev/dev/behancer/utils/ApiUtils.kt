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
    private var sClient: OkHttpClient? = null
    private var sRetrofit: Retrofit? = null
    private var sGson: Gson? = null
    private var sApi: BehanceApi? = null
    private val client: OkHttpClient?
        private get() {
            if (sClient == null) {
                val builder = OkHttpClient().newBuilder()
                builder.addInterceptor(ApiKeyInterceptor())
                if (!BuildConfig.BUILD_TYPE.contains("release")) {
                    builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
                sClient = builder.build()
            }
            return sClient
        }

    // need for interceptors
    private fun getRetrofit(): Retrofit {
            if (sGson == null) {
                sGson = Gson()
            }
            return sRetrofit ?: Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL) // need for interceptors
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(sGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

    fun getApiService(): BehanceApi {
            return sApi ?: getRetrofit().create(BehanceApi::class.java)
        }
}