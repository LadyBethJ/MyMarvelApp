package com.mjb.characters.domain.usecase

import com.mjb.characters.data.model.data.CharacterList
import com.mjb.characters.domain.repository.CharactersRepository
import com.mjb.core.interactor.BaseUseCase
import com.mjb.core.utils.State

class GetCharactersListUseCase(private val charactersRepository: CharactersRepository) :
    BaseUseCase<State<List<CharacterList>>, GetCharactersListUseCase.Params>() {
    override fun run(params: Params?) = charactersRepository.getCharactersList(params?.offset ?: 0)

    class Params(var offset: Int)
}
