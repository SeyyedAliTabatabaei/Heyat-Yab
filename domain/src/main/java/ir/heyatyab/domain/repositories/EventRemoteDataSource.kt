package ir.heyatyab.domain.repositories

import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.EventDetails
import kotlinx.coroutines.flow.Flow

interface EventRemoteDataSource {

    fun getEventList(data : Event.Request) : Flow<RemoteResultEntity<Event.Response>>

    fun getEventDetails(data : EventDetails.Request) : Flow<RemoteResultEntity<EventDetails.Response>>

}