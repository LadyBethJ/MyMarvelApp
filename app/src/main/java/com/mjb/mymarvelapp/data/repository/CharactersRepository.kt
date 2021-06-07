package com.mjb.mymarvelapp.data.repository

import com.mjb.mymarvelapp.domain.model.CharacterDetail
import com.mjb.mymarvelapp.domain.model.CharacterList
import com.mjb.mymarvelapp.infrastructure.utils.State
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharactersList(offset: Int): Flow<State<List<CharacterList>>>

    fun getCharacterDetail(id: Int): Flow<State<List<CharacterDetail>>>
}
