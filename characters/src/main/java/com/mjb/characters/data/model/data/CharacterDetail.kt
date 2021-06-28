package com.mjb.characters.data.model.data

import com.mjb.characters.data.model.view.CharacterDetailView

data class CharacterDetail(
    val name: String?,
    val image: String?,
    val description: String?,
    val comicsIds: List<Int>?,
    val seriesIds: List<Int>?
) {
    fun toCharacterDetailView() = CharacterDetailView(name, image, description)
}
