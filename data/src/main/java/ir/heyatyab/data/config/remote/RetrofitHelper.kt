package ir.heyatyab.data.config.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private const val REQUEST_TIMEOUT = 60L

    operator fun invoke(
        interceptor: MockInterceptor ,
    ) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://heyat-yab.ir/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}