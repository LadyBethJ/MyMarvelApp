package com.mjb.mymarvelapp.domain.model

import com.mjb.mymarvelapp.presentation.charactersList.models.CharacterListView

data class CharacterList(val id: Int?, val name: String?, val image: String?, val desc: String?) {
    fun toCharacterView() = CharacterListView(id, name, image, desc)
}
