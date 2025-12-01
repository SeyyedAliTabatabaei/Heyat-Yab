package ir.heyatyab.data.entities.remote


import com.google.gson.annotations.SerializedName

data class EventResponseEntity(
    @SerializedName("data") val `data`: List<Data>?,
    @SerializedName("message") val message: String?,
    @SerializedName("success") val success: Boolean?
) {
    data class Data(
        @SerializedName("address") val address: String?,
        @SerializedName("contact") val contact: String?,
        @SerializedName("datetime_end") val datetimeEnd: String?,
        @SerializedName("datetime_start") val datetimeStart: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("id") val id: Long?,
        @SerializedName("location") val locationResponseEntity: LocationResponseEntity?,
        @SerializedName("maddah") val maddah: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("poster_url") val posterUrl: String?,
        @SerializedName("repeat") val repeat: String?,
        @SerializedName("speaker") val speaker: String?
    )
}