package com.mjb.characters.data.model.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponse<T>(
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "limit")
    val limit: Int,
    @Json(name = "total")
    val total: Int,
    @Json(name = "count")
    val count: Int,
    @Json(name = "results")
    val results: List<T>
)
