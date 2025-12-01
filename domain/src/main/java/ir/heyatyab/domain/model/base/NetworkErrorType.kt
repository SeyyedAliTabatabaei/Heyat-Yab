package ir.heyatyab.domain.model.base

import ir.heyatyab.domain.model.remote.BaseResponse


sealed class NetworkErrorType(errorResponse: BaseResponse<Any?>?= null) : Throwable(errorResponse?.message){
    data class UnAuthorized(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class FieldsError(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class Forbidden(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class HeaderError(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class ResourceNotFound(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class NotAllowed(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class InternalServerError(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class NetworkConnection(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class TimeOut(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class BadRequest(val errorResponse: BaseResponse<Any?>) : NetworkErrorType(errorResponse)
    data class Unknown(val errorResponse: BaseResponse<Any?>) : NetworkErrorType()

    override fun toString(): String {
        return when(this) {
            is BadRequest -> "BadRequest:$errorResponse"
            is FieldsError -> "FieldsError:$errorResponse"
            is Forbidden -> "Forbidden:$errorResponse"
            is HeaderError -> "HeaderError:$errorResponse"
            is InternalServerError -> "InternalServerError:$errorResponse"
            is NetworkConnection -> "NetworkConnection:$errorResponse"
            is NotAllowed -> "NotAllowed:$errorResponse"
            is ResourceNotFound -> "ResourceNotFound:$errorResponse"
            is TimeOut -> "TimeOut:$errorResponse"
            is UnAuthorized -> "UnAuthorized:$errorResponse"
            is Unknown -> "Unknown:$errorResponse"
        }
    }
}
