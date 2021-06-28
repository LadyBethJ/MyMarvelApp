package com.mjb.core.data.model.api.response

data class DataResponse<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)
