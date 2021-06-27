package com.mjb.characters.data.model.api.response

import com.mjb.characters.data.model.data.CharacterDetail
import com.mjb.characters.data.model.data.CharacterList

data class CharacterResponse(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val urls: List<Url>?,
    val thumbnail: Thumbnail?,
    val comics: Comics?,
    val stories: Stories?,
    val events: Events?,
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

data class Comics(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<Item>?
)

data class Stories(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<StoriesItem>?
)

data class StoriesItem(
    val resourceURI: String?,
    val name: String?,
    val type: String?
)

data class Events(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<Item>?
)

data class Series(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<Item>?
)

data class Thumbnail(
    val extension: String?,
    val path: String?
)

data class Url(
    val type: String?,
    val url: String?
)

data class Item(
    val resourceURI: String?,
    val name: String?
)
