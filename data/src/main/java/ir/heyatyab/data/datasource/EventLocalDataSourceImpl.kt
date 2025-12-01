package ir.heyatyab.data.datasource

import ir.heyatyab.domain.model.base.ResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.repositories.EventLocalDataSource
import kotlinx.coroutines.flow.Flow

class EventLocalDataSourceImpl(

) : EventLocalDataSource {
    override fun getMyEvents(): Flow<ResultEntity<Event.Response>> {
        TODO("Not yet implemented")
    }

    override fun addMyEvent(event: Event.Response): Flow<ResultEntity<Long>> {
        TODO("Not yet implemented")
    }

    override fun removeEvent(event: Event.Response): Flow<ResultEntity<Int>> {
        TODO("Not yet implemented")
    }
}