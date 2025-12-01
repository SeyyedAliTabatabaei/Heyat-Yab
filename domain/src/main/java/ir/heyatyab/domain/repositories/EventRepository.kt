package ir.heyatyab.domain.repositories

import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.base.ResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.EventDetails
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEventList(data : Event.Request) : Flow<RemoteResultEntity<Event.Response>>

    fun getEventDetails(data : EventDetails.Request) : Flow<RemoteResultEntity<EventDetails.Response>>

    fun getMyEvents() : Flow<ResultEntity<Event.Response>>

    fun addMyEvent(event: Event.Response) : Flow<ResultEntity<Long>>

    fun removeEvent(event: Event.Response) : Flow<ResultEntity<Int>>

}