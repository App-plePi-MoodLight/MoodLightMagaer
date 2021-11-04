package com.example.moodlight.api

import com.example.moodlightmanger.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServerClient {

    // use : ServerClient.getApiService().(FunctionToUse)

    private const val baseUrl = "https://md.khjcode.com/"
    public var accessToken : String? = null

    fun getInstance() : Retrofit {

        val interceptor: Interceptor = Interceptor { chain ->
            if (accessToken != null) {
                val request: Request = chain.request().newBuilder()
                    .addHeader("Authorization", accessToken)
                    .build()
                chain.proceed(request)
            } else chain.proceed(chain.request())
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        val instance : Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return instance!!
    }

    fun getApiService() : ApiService = getInstance().create(ApiService::class.java)
}