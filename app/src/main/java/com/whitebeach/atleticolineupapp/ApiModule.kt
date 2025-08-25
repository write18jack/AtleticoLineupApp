package com.whitebeach.atleticolineupapp

import com.whitebeach.data.remote.network.PlayersInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://api.football-data.org/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        // BuildConfigからAPIキーを取得するのがベストプラクティス
        val apiKey = BuildConfig.API_KEY

        return Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("X-Auth-Token", apiKey)
                .build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            // 必要に応じて interceptor や timeout などを設定
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PlayersInfoService {
        return retrofit.create(PlayersInfoService::class.java)
    }

}