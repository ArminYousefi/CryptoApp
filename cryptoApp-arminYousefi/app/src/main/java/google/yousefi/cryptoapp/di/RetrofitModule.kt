package google.yousefi.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import google.yousefi.cryptoapp.BuildConfig
import google.yousefi.cryptoapp.common.Constants
import google.yousefi.cryptoapp.data.source.remote.CoinApi
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ماژول Hilt برای فراهم کردن پیاده‌سازی Retrofit و OkHttpClient.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    /**
     * فراهم کردن پیاده‌سازی CoinApi با استفاده از Retrofit.
     *
     * @param retrofit نمونه‌ای از Retrofit.
     * @return پیاده‌سازی CoinApi.
     */
    @Provides
    @Singleton
    fun provideCoinApi(retrofit: Retrofit): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    /**
     * فراهم کردن نمونه‌ای از Retrofit با استفاده از OkHttpClient.
     *
     * @param okHttpClient نمونه‌ای از OkHttpClient.
     * @return نمونه‌ای از Retrofit.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * فراهم کردن نمونه‌ای از OkHttpClient با تنظیمات دلخواه.
     *
     * @return نمونه‌ای از OkHttpClient.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val apiKeyInterceptor = Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .header("x-access-token", BuildConfig.API_KEY)
                .build()

            chain.proceed(newRequest)
        }

        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}
