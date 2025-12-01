package ir.heyatyab.domain.model.base

sealed class RemoteResultEntity<out R> {
    data object Loading : RemoteResultEntity<Nothing>()
    data class Success<out T>(val data: T) : RemoteResultEntity<T>()
    data class NetworkError(val networkError: NetworkErrorType?): RemoteResultEntity<Nothing>()
    data object NoInternet: RemoteResultEntity<Nothing>()

    override fun toString(): String {
        return when(this) {
            Loading -> "Loading ..."
            is NetworkError -> "Error $networkError"
            NoInternet -> "No internet connection!"
            is Success<*> -> "Success[data=$data]"
        }
    }
}
