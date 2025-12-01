package ir.heyatyab.domain.model.remote

open class BaseResponse<out T>(
    open val success: Boolean?,
    open val message: String?,
    open val data: T?
) {
    override fun toString(): String {
        return "success:${this.success} - message:${this.message} - data:${this.data}"
    }
}