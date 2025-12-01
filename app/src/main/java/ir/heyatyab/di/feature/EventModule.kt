package ir.heyatyab.di.feature

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.heyatyab.data.config.remote.EventApiService
import ir.heyatyab.data.datasource.EventLocalDataSourceImpl
import ir.heyatyab.data.datasource.EventRemoteDataSourceImpl
import ir.heyatyab.data.repository.EventRepositoryImpl
import ir.heyatyab.domain.repositories.EventLocalDataSource
import ir.heyatyab.domain.repositories.EventRemoteDataSource
import ir.heyatyab.domain.repositories.EventRepository
import ir.heyatyab.domain.usecase.GetEventDetailsUseCase
import ir.heyatyab.domain.usecase.GetEventListUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventModule {

    // DataSource
    @Singleton
    @Provides
    fun provideRemoteDataSource(eventApiService: EventApiService) : EventRemoteDataSource {
        return EventRemoteDataSourceImpl(eventApiService)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource() : EventLocalDataSource {
        return EventLocalDataSourceImpl()
    }

    // Repository
    @Singleton
    @Provides
    fun provideRepository(eventRemoteDataSource: EventRemoteDataSource , eventLocalDataSource: EventLocalDataSource) : EventRepository {
        return EventRepositoryImpl(eventLocalDataSource, eventRemoteDataSource)
    }


    // UseCase
    @Singleton
    @Provides
    fun provideGetEventListUseCase(eventRepository: EventRepository) : GetEventListUseCase = GetEventListUseCase(eventRepository , Dispatchers.IO)

    @Singleton
    @Provides
    fun provideGetEventDetailsUseCase(eventRepository: EventRepository) : GetEventDetailsUseCase = GetEventDetailsUseCase(eventRepository , Dispatchers.IO)



}