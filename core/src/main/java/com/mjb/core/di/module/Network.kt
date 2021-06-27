package com.mjb.core.di.module

import com.mjb.core.BuildConfig
import com.mjb.core.network.NetworkHandler
//import com.mjb.core.network.NetworkHandlerImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    factory { NetworkHandler(get()) }
    //factory { NetworkHandlerImpl(get()) }

    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(createOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    //TODO esto no sé si lo necesito
    /*single {
        Retrofit.Builder()
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
    }*/
}

private fun createOkHttpClient(): OkHttpClient {
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
