package com.paysky.momogrow.data.api

import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.paysky.momogrow.BuildConfig
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.PreferenceProcessor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClientMomo {
    var BASE_URL: String = "https://emanfateen.mtngrow.com"
    fun apiClient(url: String = BASE_URL): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request =
//                    chain.request().newBuilder().addHeader(
//                        "Authorization",
//                        "" + PreferenceProcessor.getStr(
//                            Constants.Companion.Preference.AUTH_TOKEN,
//                            ""
//                        )
//                    ).build()
                    chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer 1637134988Z1Gw0oZPomBUzs4rCzM1AFQZH88hgQsMoHABNc3fz0gGNK6g"
                        )
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .build()
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