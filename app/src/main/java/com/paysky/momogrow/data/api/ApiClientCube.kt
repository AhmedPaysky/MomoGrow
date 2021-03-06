package com.paysky.momogrow.data.api

import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.paysky.momogrow.BuildConfig
import com.paysky.momogrow.R2
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.PreferenceProcessor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.Response
import com.paysky.momogrow.R2.id.chain
import okhttp3.Request


object ApiClientCube {
    // base url
    var BASE_URL: String = "https://mtn.paysky.io/cube/"
    fun apiClient(url: String = BASE_URL): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request =
                    chain.request().newBuilder().addHeader(
                        "Authorization",
                        "" + PreferenceProcessor.getStr(
                            Constants.Companion.Preference.AUTH_TOKEN,
                            ""
                        )
                    ).build()
                return chain.proceed(request)
            }

        }

//        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(40, TimeUnit.SECONDS)
        httpClient.connectTimeout(40, TimeUnit.SECONDS)
        httpClient.writeTimeout(400, TimeUnit.SECONDS)


        if (PreferenceProcessor.getBool(Constants.Companion.Preference.IS_LOGIN, false)) {
            httpClient.addInterceptor(interceptor)
        }
        httpClient.addInterceptor(
            LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .request("Request")
                .response("SimpleResponse")
                .build()
//                .addHeader("version", PackageInfoUtil.getAppVersionNumber(context))
        )

        val client: OkHttpClient = httpClient.build()
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}