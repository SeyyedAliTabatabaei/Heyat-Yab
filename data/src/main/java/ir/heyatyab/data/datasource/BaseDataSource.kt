package ir.heyatyab.data.datasource

import com.google.gson.Gson
import ir.heyatyab.data.utils.GsonManager
import ir.heyatyab.domain.model.base.NetworkErrorType
import ir.heyatyab.domain.model.base.RemoteResultEntity
import ir.heyatyab.domain.model.base.ResultEntity
import ir.heyatyab.domain.model.remote.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseDataSource {

    suspend fun <T> safeQueryDb(query : suspend () -> T) : ResultEntity<T> {
        return try {
            query.invoke().let {
                ResultEntity.Success(it)
            }
        } catch (e : Exception) {
            ResultEntity.Error(e.message.toString())
        }
    }


    suspend fun <T>safeApiCall(apiCall : suspend () -> T) : Flow<RemoteResultEntity<T>> = flow {
        emit(RemoteResultEntity.Loading)

        emit(
            try {
                val result = apiCall.invoke()
                RemoteResultEntity.Success(result)
            } catch (e: SocketTimeoutException) {
                RemoteResultEntity.NetworkError(NetworkErrorType.TimeOut(BaseResponse(false , null , null)))
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> RemoteResultEntity.NetworkError(extractHttpExceptions(throwable))
                    is IOException -> RemoteResultEntity.NetworkError(NetworkErrorType.NetworkConnection(BaseResponse(false , throwable.message , null)))
                    else -> RemoteResultEntity.NetworkError(NetworkErrorType.Unknown(BaseResponse(false , throwable.message ?: "خطای ناشناخته" , null)))
                }
            }
        )
    }

    private fun extractHttpExceptions(ex: HttpException): NetworkErrorType {
        return try {
            val errorResponse = extractErrorMessage(ex)
            when (ex.code()) {
                UNAUTHORIZED -> NetworkErrorType.UnAuthorized(errorResponse = errorResponse)
                FIELDS_ERROR -> NetworkErrorType.FieldsError(errorResponse = errorResponse)
                FORBIDDEN -> NetworkErrorType.Forbidden(errorResponse = errorResponse)
                NOT_ALLOWED -> NetworkErrorType.NotAllowed(errorResponse = errorResponse)
                INTERNAL_SERVER_ERROR -> NetworkErrorType.InternalServerError(errorResponse = errorResponse)
                BAD_REQUEST -> NetworkErrorType.BadRequest(errorResponse = errorResponse)
                NOT_FOUND -> NetworkErrorType.ResourceNotFound(errorResponse = errorResponse)
                HEADER_ERROR -> NetworkErrorType.HeaderError(errorResponse = errorResponse)
                else -> NetworkErrorType.Unknown(errorResponse = errorResponse)
            }
        } catch (exception: Exception) {
            NetworkErrorType.Unknown(errorResponse = BaseResponse(false , exception.message , null))
        }
    }

    private fun extractErrorMessage(ex: HttpException): BaseResponse<Any?> {
        return try {
            val bodyString = ex.response()?.errorBody()?.string() ?: ""
            GsonManager.convertToGson(bodyString , BaseResponse::class.java) ?: BaseResponse(false , "know error" , null)
        } catch (e: Exception) {
            BaseResponse(false , e.message , null)
        }
    }

    companion object{
        private const val BAD_REQUEST = 400
        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404
        private const val NOT_ALLOWED = 405
        private const val HEADER_ERROR = 412
        private const val FIELDS_ERROR = 422
        private const val RATELIMIT_ERROR = 429
        private const val INTERNAL_SERVER_ERROR = 500
    }


}