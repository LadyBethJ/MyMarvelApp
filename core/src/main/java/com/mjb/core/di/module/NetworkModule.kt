package com.mjb.core.di.module

import android.app.Application
import android.content.Context
import com.mjb.core.BuildConfig
// ESTO DEBERIA ESTAR EN MODULO CHARACTERS
//import com.mjb.mymarvelapp.data.datasource.api.service.*
import com.mjb.core.network.NetworkHandler
import com.mjb.core.network.NetworkHandlerImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(
    val application: Application
) {

    // TODO ESTO DEBERIA ESTAR EN MODULO CHARACTERS
    //@Provides
    //fun provideCharactersService(retrofit: Retrofit): CharactersApi = CharactersService(retrofit)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)

        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val apiKey = BuildConfig.PUBLIC_KEY
            val hash = BuildConfig.HASH
            val ts = BuildConfig.TS

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build()

            val requestBuilder = original.newBuilder().url(url)

            chain.proceed(requestBuilder.build())
        }

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    fun providesNetworkHandler(context: Context): NetworkHandler = NetworkHandlerImpl(context)
}
