package com.mjb.characters.domain.usecase

import com.mjb.characters.data.model.data.CharacterDetail
import com.mjb.characters.domain.repository.CharactersRepository
import com.mjb.core.interactor.BaseUseCase
import com.mjb.core.utils.State

class GetCharacterDetailUseCase(private val charactersRepository: CharactersRepository) :
    BaseUseCase<State<List<CharacterDetail>>, GetCharacterDetailUseCase.Params>() {
    override fun run(params: Params?) = charactersRepository.getCharacterDetail(params?.id ?: 0)

    class Params(var id: Int)
}
