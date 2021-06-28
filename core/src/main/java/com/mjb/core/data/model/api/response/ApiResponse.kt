package com.mjb.core.data.model.api.response

data class ApiResponse<T>(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: DataResponse<T>?,
    val etag: String?
)
