package com.gitlab.kordlib.rest.request

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.rest.route.Route
import io.ktor.http.*
import kotlinx.serialization.SerializationStrategy

class RequestBuilder<T>(private val route: Route<T>, keySize: Int = 2) {

    val keys: MutableMap<Route.Key, String> = HashMap(keySize, 1f)

    operator fun MutableMap<Route.Key, String>.set(key: Route.Key, value: Snowflake) = set(key, value.asString)

    private val headers = HeadersBuilder()
    private val parameters = ParametersBuilder()

    private var body: RequestBody<*>? = null
    private val files: MutableList<Pair<String, java.io.InputStream>> = mutableListOf()

    operator fun MutableMap<String, String>.set(key: Route.Key, value: String) = set(key.identifier, value)

    fun <E : Any> body(strategy: SerializationStrategy<E>, body: E) {
        this.body = RequestBody(strategy, body)
    }

    fun parameter(key: String, value: Snowflake) = parameters.append(key, value.value.toString())

    fun parameter(key: String, value: Any) = parameters.append(key, value.toString())

    fun header(key: String, value: String) = headers.append(key, value.encodeURLQueryComponent())

    fun file(name: String, input: java.io.InputStream) {
        files.add(name to input)
    }

    fun file(pair: Pair<String, java.io.InputStream>) {
        files.add(pair)
    }

    fun build(): Request<*, T> = when {
        files.isEmpty() -> JsonRequest(route, keys, parameters.build(), headers.build(), body)
        else -> MultipartRequest(route, keys, parameters.build(), headers.build(), body, files.orEmpty())
    }

}
