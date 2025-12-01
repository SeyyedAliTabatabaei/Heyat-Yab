package ir.heyatyab.data.entities.remote


import com.google.gson.annotations.SerializedName
import ir.heyatyab.domain.model.remote.Event

data class EventDetailsResponseEntity(
    @SerializedName("data") val `data`: EventResponseEntity.Data?,
    @SerializedName("message") val message: String?,
    @SerializedName("success") val success: Boolean?
)