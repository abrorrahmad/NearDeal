package com.abrorrahmad.neardeal.data

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient{
    private val BASE_URL = "http://192.168.1.19/neardeal/"
    private var retrofit : Retrofit? = null

            fun getClient(context : Context) : Retrofit{
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(OkHttpClient.Builder().apply {
                            connectTimeout(1000, TimeUnit.SECONDS)
                            readTimeout(1000, TimeUnit.SECONDS)
                            writeTimeout(1000, TimeUnit.SECONDS)

                            val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
                            cookieJar(cookieJar)

                            val interceptor = HttpLoggingInterceptor()
                            interceptor.level = HttpLoggingInterceptor.Level.BODY
                            addInterceptor(interceptor)
                        } .build()

                        )
                        .build()

                }
                return retrofit!!
            }
}