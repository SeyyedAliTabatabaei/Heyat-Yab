package ir.heyatyab.data.entities.remote.mappers

import ir.heyatyab.data.entities.remote.EventDetailsResponseEntity
import ir.heyatyab.data.entities.remote.EventResponseEntity
import ir.heyatyab.data.entities.remote.LocationResponseEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.EventDetails
import ir.heyatyab.domain.model.remote.Location
import ir.heyatyab.domain.model.remote.RepeatType

fun EventDetails.Response.toEntity() : EventDetailsResponseEntity = EventDetailsResponseEntity(
    success = this.success ,
    message = this.message ?: "unknown error" ,
    data = this.data?.toEntity()
)

fun EventDetailsResponseEntity.toDomain() : EventDetails.Response = EventDetails.Response(
    success = this.success ,
    message = this.message ,
    data = this.data?.toDomain()
)
