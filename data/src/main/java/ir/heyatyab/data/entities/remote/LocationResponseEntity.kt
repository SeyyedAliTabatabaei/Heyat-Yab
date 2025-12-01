package ir.heyatyab.data.entities.remote


import com.google.gson.annotations.SerializedName

data class LocationResponseEntity(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)