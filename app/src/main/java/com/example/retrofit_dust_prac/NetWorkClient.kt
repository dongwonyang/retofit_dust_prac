package com.example.retrofit_dust_prac

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//https://www.data.go.kr/data/15073861/openapi.do
object NetWorkClient {

    private const val DUST_BASE_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/"


    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = AuthorizationInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val dustRetrofit = Retrofit.Builder()
        .baseUrl(DUST_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(
            createOkHttpClient()
        ).build()

    val dustNetWork: NetWorkInterface = dustRetrofit.create(NetWorkInterface::class.java)
}
