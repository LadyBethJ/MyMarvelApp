package com.mjb.characters.utils

import com.mjb.characters.data.model.api.response.*
import com.mjb.core.data.model.api.response.ApiResponse
import com.mjb.core.data.model.api.response.DataResponse
import com.mjb.core.extensions.empty

fun <T> mockApiResponse(list: List<T>) = ApiResponse(
    code = 0,
    status = String.empty(),
    copyright = String.empty(),
    attributionText = String.empty(),
    attributionHTML = String.empty(),
    data = mockDataResponse(list),
    etag = String.empty()
)

fun <T> mockDataResponse(list: List<T>) = DataResponse(
    offset = 0,
    limit = 0,
    total = 0,
    count = 0,
    results = list
)

fun mockCharacters(amount: Int): List<CharacterResponse> {
    val charactersList = mutableListOf<CharacterResponse>()

    val character = mockCharacter()

    for (i: Int in 1..amount) {
        charactersList.add(character)
    }
    return charactersList
}

fun mockCharacter() = CharacterResponse(
    id = 0,
    name = "Iron man",
    description = String.empty(),
    modified = String.empty(),
    resourceURI = String.empty(),
    urls = listOf(Url(String.empty(), String.empty())),
    thumbnail = Thumbnail(String.empty(), String.empty()),
    comics = Comics(
        available = 0,
        returned = 0,
        collectionURI = String.empty(),
        items = listOf(Item(resourceURI = String.empty(), name = String.empty()))
    ),
    stories = Stories(
        available = 0,
        returned = 0,
        collectionURI = String.empty(),
        items = listOf(StoriesItem(
            resourceURI = String.empty(),
            name = String.empty(),
            type = String.empty()
        ))
    ),
    events = Events(
        available = 0,
        returned = 0,
        collectionURI = String.empty(),
        items = listOf(Item(resourceURI = String.empty(), name = String.empty()))
    ),
    series = Series(
        available = 0,
        returned = 0,
        collectionURI = String.empty(),
        items = listOf(Item(resourceURI = String.empty(), name = String.empty()))
    )
)