package com.vb.yelplite.app.data

import com.vb.yelplite.app.AppConstants
import com.vb.yelplite.app.domain.Business
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.koin.dsl.module


val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    factory { provideYelpApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BASIC)
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://api.yelp.com/v3/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}

fun provideYelpApi(retrofit: Retrofit): YelpApi = retrofit.create(YelpApi::class.java)

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${AppConstants.yelpApiKey}")
            .build()
        return chain.proceed(newRequest)
    }
}

data class YelpBusinessSearchResponse(
    val total: Int,
    val businesses: List<Business>
)