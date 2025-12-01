package ir.heyatyab.domain.usecase

import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.EventDetails
import ir.heyatyab.domain.repositories.EventRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetEventDetailsUseCase(
    private val eventRepository: EventRepository ,
    private val coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<EventDetails.Request , RemoteResultEntity<EventDetails.Response>>(coroutineDispatcher) {

    override fun execute(parameters: EventDetails.Request): Flow<RemoteResultEntity<EventDetails.Response>> {
        return eventRepository.getEventDetails(parameters)
    }


}