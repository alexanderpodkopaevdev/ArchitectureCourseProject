package com.alexanderPodkopaev.dev.behancer.di

import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.data.api.ApiKeyInterceptor
import com.alexanderPodkopaev.dev.behancer.data.api.BehanceApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module
import toothpick.ktp.binding.bind


class NetworkModule : Module() {

    private var mClient: OkHttpClient = getClient()
    private var mGson: Gson = getGson()
    private var mRetrofit: Retrofit = getRetrofit()

    init {
        bind(Gson::class).toInstance(mGson)
        bind(OkHttpClient::class.java).toInstance(mClient)
        bind(Retrofit::class.java).toInstance(mRetrofit)
        bind(BehanceApi::class.java).toInstance(getApiService())
    }

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(ApiKeyInterceptor())
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }

    private fun getGson() = Gson()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun getApiService() = mRetrofit.create(BehanceApi::class.java)

}