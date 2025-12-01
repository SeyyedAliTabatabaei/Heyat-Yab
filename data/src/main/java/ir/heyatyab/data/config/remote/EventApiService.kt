package ir.heyatyab.data.config.remote

import com.google.gson.JsonObject
import ir.heyatyab.data.entities.remote.EventDetailsResponseEntity
import ir.heyatyab.data.entities.remote.EventResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApiService {

    @GET("events")
    suspend fun getEventList() : EventResponseEntity

    @GET("event-details/{id}")
    suspend fun getEventDetails(@Path("id") id: String) : EventDetailsResponseEntity


}