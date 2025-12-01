package ir.heyatyab.domain.repositories

import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.base.ResultEntity
import ir.heyatyab.domain.model.remote.Event
import kotlinx.coroutines.flow.Flow

interface EventLocalDataSource {

    fun getMyEvents() : Flow<ResultEntity<Event.Response>>

    fun addMyEvent(event: Event.Response) : Flow<ResultEntity<Long>>

    fun removeEvent(event: Event.Response) : Flow<ResultEntity<Int>>
}