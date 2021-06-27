package com.mjb.characters.data.model.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "copyright")
    val copyright: String?,
    @Json(name = "attributionText")
    val attributionText: String?,
    @Json(name = "attributionHTML")
    val attributionHTML: String?,
    @Json(name = "data")
    val apiDataResponse: DataResponse<T>?,
    @Json(name = "etag")
    val etag: String?
)
