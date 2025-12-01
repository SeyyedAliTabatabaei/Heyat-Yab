package ir.heyatyab.data.repository

import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.base.ResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.EventDetails
import ir.heyatyab.domain.repositories.EventLocalDataSource
import ir.heyatyab.domain.repositories.EventRemoteDataSource
import ir.heyatyab.domain.repositories.EventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val eventLocalDataSource: EventLocalDataSource,
    private val eventRemoteDataSource: EventRemoteDataSource
) : EventRepository {

    override fun getEventList(data: Event.Request): Flow<RemoteResultEntity<Event.Response>> {
        return eventRemoteDataSource.getEventList(data)
    }

    override fun getEventDetails(data: EventDetails.Request): Flow<RemoteResultEntity<EventDetails.Response>> {
        return eventRemoteDataSource.getEventDetails(data)
    }

    override fun getMyEvents(): Flow<ResultEntity<Event.Response>> {
        return eventLocalDataSource.getMyEvents()
    }

    override fun addMyEvent(event: Event.Response): Flow<ResultEntity<Long>> {
        return eventLocalDataSource.addMyEvent(event)
    }

    override fun removeEvent(event: Event.Response): Flow<ResultEntity<Int>> {
        return eventLocalDataSource.removeEvent(event)
    }

}