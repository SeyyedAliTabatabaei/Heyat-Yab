package ir.heyatyab.data.config.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()

        return when {
            url.endsWith("events") -> mockJsonResponse(chain, "mock_events.json")
            url.contains("event-details") -> {
                val id = url.substringAfterLast("/")
                val fileName = "mock_event_$id.json"
                mockJsonResponse(chain, fileName)
            }
            else -> chain.proceed(request)
        }
    }

    private fun mockJsonResponse(chain: Interceptor.Chain, fileName: String): Response {
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }

        return Response.Builder()
            .code(200)
            .message(json)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(
                json.toByteArray().toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}