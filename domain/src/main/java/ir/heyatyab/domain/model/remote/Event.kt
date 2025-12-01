package ir.heyatyab.domain.model.remote

sealed class Event{

    data class Response(
        override val success: Boolean?,
        override val message: String?,
        override val data: List<Data>?
    ) : BaseResponse<List<Response.Data>?>(success, message,data){
        data class Data(
            val id : Long? ,
            val name : String? ,
            val speaker : String? ,
            val maddah : String ? ,
            val address : String? ,
            val datetimeStart : String? ,
            val datetimeEnd : String? ,
            val description : String? ,
            val posterUrl : String? ,
            val contact : String? ,
            val repeatType: RepeatType? ,
            val location : Location?
        )
    }

    data class Request(
        val name : String ?= null ,
        val userLocation : Location ?= null
    )
}
