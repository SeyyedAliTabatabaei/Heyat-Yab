package ir.heyatyab.domain.model.remote

sealed class EventDetails{

    data class Response(
        override val success: Boolean?,
        override val message: String?,
        override val data: Event.Response.Data?
    ) : BaseResponse<Event.Response.Data?>(success, message,data)

    data class Request(
        val id : Long ,
    )
}
