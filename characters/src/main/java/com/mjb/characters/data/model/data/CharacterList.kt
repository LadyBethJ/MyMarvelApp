package com.mjb.characters.data.model.data

import com.mjb.characters.data.model.view.CharacterListView

data class CharacterList(
    val id: Int?,
    val name: String?,
    val image: String?,
    val desc: String?
) {
    fun toCharacterView() = CharacterListView(id, name, image, desc)
}
