package ir.heyatyab.data.entities.remote.mappers

import ir.heyatyab.data.entities.remote.EventResponseEntity
import ir.heyatyab.data.entities.remote.LocationResponseEntity
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.domain.model.remote.Location
import ir.heyatyab.domain.model.remote.RepeatType

fun Event.Response.toEntity() : EventResponseEntity = EventResponseEntity(
    success = this.success ,
    message = this.message ?: "unknown error" ,
    data = this.data?.map { it.toEntity() }
)

fun EventResponseEntity.toDomain() : Event.Response = Event.Response(
    success = this.success ,
    message = this.message ,
    data = this.data?.map { it.toDomain() }
)

fun Event.Response.Data.toEntity() : EventResponseEntity.Data = EventResponseEntity.Data(
    id = id ,
    name = name ,
    speaker = speaker ,
    maddah = maddah ,
    address = address ,
    datetimeStart = datetimeStart ,
    datetimeEnd = datetimeEnd ,
    contact = contact ,
    description = description ,
    posterUrl = posterUrl ,
    repeat = repeatType?.name ,
    locationResponseEntity = if (location == null) null else LocationResponseEntity(
        lat = location!!.lat ,
        lng = location!!.lng
    )
)

fun EventResponseEntity.Data.toDomain() : Event.Response.Data = Event.Response.Data(
    id = id ,
    name = name ,
    speaker = speaker ,
    maddah = maddah ,
    address = address ,
    datetimeStart = datetimeStart ,
    datetimeEnd = datetimeEnd ,
    contact = contact ,
    description = description ,
    posterUrl = posterUrl ,
    repeatType = RepeatType.valueOf(repeat ?: "ONCE") ,
    location = if (locationResponseEntity == null) null else Location(
        lat = locationResponseEntity.lat ,
        lng = locationResponseEntity.lng
    )
)