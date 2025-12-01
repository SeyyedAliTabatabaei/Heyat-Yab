package ir.heyatyab.domain.usecase

import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.repositories.EventRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetEventListUseCase(
    private val eventRepository: EventRepository ,
    private val coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<Event.Request , RemoteResultEntity<Event.Response>>(coroutineDispatcher) {

    override fun execute(parameters: Event.Request): Flow<RemoteResultEntity<Event.Response>> {
        return eventRepository.getEventList(parameters)
    }


}