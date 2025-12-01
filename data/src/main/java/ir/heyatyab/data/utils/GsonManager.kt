package ir.heyatyab.data.utils

import com.google.gson.Gson

object GsonManager {

    val gson : Gson = Gson()

    fun convertToString(data : Any?) : String {
        return gson.toJson(data)
    }

    fun <T>convertToGson(data : String? , type : Class<T>) : T? {
        return gson.fromJson(data , type)
    }

    inline fun <reified T> convertToObject(json: String?): T? {
        if (json == null) return null
        val type = object : com.google.gson.reflect.TypeToken<T>() {}.type
        return gson.fromJson<T>(json, type)
    }


}