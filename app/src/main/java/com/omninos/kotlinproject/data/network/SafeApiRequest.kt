package com.omninos.kotlinproject.data.network

import com.omninos.kotlinproject.util.ApiException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))

                } catch (e: Exception) {
                    message.append("\n")

                }
            }
            message.append("Error code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}