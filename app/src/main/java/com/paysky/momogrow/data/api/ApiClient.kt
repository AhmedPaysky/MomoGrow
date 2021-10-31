package com.paysky.momogrow.data.api

import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.paysky.momogrow.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient


object ApiClient {
    // base url
    var BASE_URL: String = "https://mtn.paysky.io/cube/"
    fun apiClient(url: String = BASE_URL): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(100, TimeUnit.SECONDS)
        httpClient.connectTimeout(40, TimeUnit.SECONDS)
        httpClient.writeTimeout(100, TimeUnit.SECONDS)
        httpClient.addInterceptor(
            LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BODY)
                .request("Request")
                .response("Response")
//                .addHeader("version", PackageInfoUtil.getAppVersionNumber(context))
                .build()
        )

        val client: OkHttpClient = httpClient.build()
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}