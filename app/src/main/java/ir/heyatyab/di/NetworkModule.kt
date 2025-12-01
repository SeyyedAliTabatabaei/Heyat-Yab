package ir.heyatyab.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.heyatyab.data.config.remote.EventApiService
import ir.heyatyab.data.config.remote.MockInterceptor
import ir.heyatyab.data.config.remote.RetrofitHelper
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Singleton
    @Provides
    fun provideMockInterceptor(@ApplicationContext context: Context): MockInterceptor {
        return MockInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideRetrofit(mockInterceptor: MockInterceptor): Retrofit {
        return RetrofitHelper.invoke(mockInterceptor)
    }

    // Api Services
    @Singleton
    @Provides
    fun provideEventApiService(retrofit: Retrofit) : EventApiService {
        return retrofit.create(EventApiService::class.java)
    }

}
