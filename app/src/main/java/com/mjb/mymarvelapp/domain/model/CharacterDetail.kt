package com.mjb.mymarvelapp.domain.model

import com.mjb.mymarvelapp.presentation.characterDetail.models.CharacterDetailView

data class CharacterDetail(
    val name: String,
    val image: String,
    val description: String,
    val comicsIds: List<Int>,
    val seriesIds: List<Int>
) {
    fun toCharacterDetailView() = CharacterDetailView(name, image, description)
}
