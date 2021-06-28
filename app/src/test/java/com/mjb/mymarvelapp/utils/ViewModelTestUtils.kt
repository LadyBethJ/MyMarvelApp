package com.mjb.mymarvelapp.utils

import com.mjb.characters.data.model.api.response.*
import com.mjb.core.extensions.empty

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
    comics = mockComic(),
    stories = mockStories(),
    events = mockEvents(),
    series = mockSeries()
)

fun mockComic() = Comics(
    available = 0,
    returned = 0,
    collectionURI = String.empty(),
    items = listOf(Item(resourceURI = String.empty(), name = String.empty()))
)

fun mockStories() = Stories(
    available = 0,
    returned = 0,
    collectionURI = String.empty(),
    items = listOf(
        StoriesItem(
            resourceURI = String.empty(),
            name = String.empty(),
            type = String.empty()
        )
    )
)

fun mockEvents() = Events(
    available = 0,
    returned = 0,
    collectionURI = String.empty(),
    items = listOf(Item(resourceURI = String.empty(), name = String.empty()))
)

fun mockSeries() = Series(
    available = 0,
    returned = 0,
    collectionURI = String.empty(),
    items = listOf(Item(resourceURI = String.empty(), name = String.empty()))
)