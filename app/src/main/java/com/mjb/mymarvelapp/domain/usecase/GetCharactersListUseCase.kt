package com.mjb.mymarvelapp.domain.usecase

import com.mjb.mymarvelapp.data.repository.CharactersRepository
import com.mjb.mymarvelapp.domain.model.CharacterList
import com.mjb.mymarvelapp.infrastructure.utils.State

class GetCharactersListUseCase(private val charactersRepository: CharactersRepository) :
    BaseUseCase<State<List<CharacterList>>, GetCharactersListUseCase.Params>() {
    override fun run(params: Params?) = charactersRepository.getCharactersList(params?.offset ?: 0)

    class Params(var offset: Int)
}
