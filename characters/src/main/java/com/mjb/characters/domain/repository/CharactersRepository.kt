package com.mjb.characters.domain.repository

import com.mjb.characters.data.model.data.CharacterDetail
import com.mjb.characters.data.model.data.CharacterList
import com.mjb.core.utils.State
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharactersList(offset: Int): Flow<State<List<CharacterList>>>

    fun getCharacterDetail(id: Int): Flow<State<List<CharacterDetail>>>
}
