package ir.heyatyab.data.datasource

import com.google.gson.JsonObject
import ir.heyatyab.data.config.remote.EventApiService
import ir.heyatyab.data.entities.remote.mappers.toDomain
import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.EventDetails
import ir.heyatyab.domain.repositories.EventRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class EventRemoteDataSourceImpl(
    private val eventApiService: EventApiService
) : EventRemoteDataSource , BaseDataSource() {

    override fun getEventList(data: Event.Request): Flow<RemoteResultEntity<Event.Response>> = flow {
        emitAll(
            safeApiCall {
                eventApiService.getEventList().toDomain()
            }
        )
    }

    override fun getEventDetails(data: EventDetails.Request): Flow<RemoteResultEntity<EventDetails.Response>> = flow {
        emitAll(
            safeApiCall {
                eventApiService.getEventDetails(data.id.toString()).toDomain()
            }
        )
    }

}