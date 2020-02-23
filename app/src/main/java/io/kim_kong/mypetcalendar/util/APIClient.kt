package io.kim_kong.mypetcalendar.util

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    private val URL = "http://13.209.17.233:3000"
    private var mRetrofit: Retrofit? = null

    val instance: Retrofit
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build()

            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mRetrofit!!
        }
}