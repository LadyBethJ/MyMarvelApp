package com.mjb.mymarvelapp.domain.usecase

import com.mjb.mymarvelapp.data.repository.CharactersRepository
import com.mjb.mymarvelapp.domain.model.CharacterDetail
import com.mjb.mymarvelapp.infrastructure.utils.State

class GetCharacterDetailUseCase(private val charactersRepository: CharactersRepository) :
    BaseUseCase<State<List<CharacterDetail>>, GetCharacterDetailUseCase.Params>() {
    override fun run(params: Params?) = charactersRepository.getCharacterDetail(params?.id ?: 0)

    class Params(var id: Int)
}
