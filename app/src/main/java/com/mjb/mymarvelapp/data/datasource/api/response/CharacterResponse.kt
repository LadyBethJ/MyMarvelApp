package com.mjb.mymarvelapp.data.datasource.api.response

import com.mjb.mymarvelapp.domain.model.CharacterDetail
import com.mjb.mymarvelapp.domain.model.CharacterList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "modified")
    val modified: String?,
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "urls")
    val urls: List<Url>?,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail?,
    @Json(name = "comics")
    val comics: Comics?,
    @Json(name = "stories")
    val stories: Stories?,
    @Json(name = "events")
    val events: Events?,
    @Json(name = "series")
    val series: Series?
) {
    fun toCharacterListDomain() =
        CharacterList(id, name, (thumbnail?.path + "." + thumbnail?.extension), description)

    fun toCharacterDetailDomain() =
        CharacterDetail(
            name,
            (thumbnail?.path + "." + thumbnail?.extension),
            description,
            comics?.items?.map {
                if (it.resourceURI?.isNotEmpty() == true) {
                    it.resourceURI.substring(it.resourceURI.lastIndexOf('/') + 1).toInt()
                } else {
                    0
                }
            },
            series?.items?.map {
                if (it.resourceURI?.isNotEmpty() == true) {
                    it.resourceURI.substring(it.resourceURI.lastIndexOf('/') + 1).toInt()
                } else {
                    0
                }
            }
        )
}

@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val collectionURI: String?,
    @Json(name = "items")
    val items: List<Item>?
)

@JsonClass(generateAdapter = true)
data class Stories(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val collectionURI: String?,
    @Json(name = "items")
    val items: List<StoriesItem>?
)

@JsonClass(generateAdapter = true)
data class StoriesItem(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "type")
    val type: String?
)

@JsonClass(generateAdapter = true)
data class Events(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val collectionURI: String?,
    @Json(name = "items")
    val items: List<Item>?
)

@JsonClass(generateAdapter = true)
data class Series(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val collectionURI: String?,
    @Json(name = "items")
    val items: List<Item>?
)

@JsonClass(generateAdapter = true)
data class Thumbnail(
    @Json(name = "extension")
    val extension: String?,
    @Json(name = "path")
    val path: String?
)

@JsonClass(generateAdapter = true)
data class Url(
    @Json(name = "type")
    val type: String?,
    @Json(name = "url")
    val url: String?
)

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?
)
