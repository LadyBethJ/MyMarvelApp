package com.mjb.mymarvelapp.characters.usecase

import com.mjb.mymarvelapp.data.datasource.api.service.CharactersService
import com.mjb.mymarvelapp.data.repository.CharactersRepositoryImpl
import com.mjb.mymarvelapp.domain.model.CharacterDetail
import com.mjb.mymarvelapp.domain.usecase.GetCharacterDetailUseCase
import com.mjb.mymarvelapp.infrastructure.network.NetworkHandler
import com.mjb.mymarvelapp.infrastructure.utils.State
import com.mjb.mymarvelapp.infrastructure.utils.Success
import com.mjb.mymarvelapp.utils.mockApiResponse
import com.mjb.mymarvelapp.utils.mockCharacters
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import retrofit2.Response

class GetCharacterDetailUseCaseTest {

    @Test
    fun `should get all characters on success`() = runBlocking {
        // GIVEN
        val repository: CharactersRepositoryImpl
        val character = mockCharacters(1)
        val mockResponse = mockApiResponse(character)
        val characterId = 0

        // WHEN
        val service = mock<CharactersService> {
            onBlocking { getCharacterDetail(characterId) } doReturn Response.success(mockResponse)
        }

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }

        repository = CharactersRepositoryImpl(service, networkHandler)
        val getCharacterDetailUseCase: GetCharacterDetailUseCase = GetCharacterDetailUseCase(repository)

        val flow: Flow<State<List<CharacterDetail>>> =
            getCharacterDetailUseCase.run(GetCharacterDetailUseCase.Params(characterId))

        // THEN
        flow.collect { result ->
            result.`should be instance of`<Success<List<CharacterDetail>>>()
            when (result) {
                is Success<List<CharacterDetail>> -> {
                    result.data shouldBeEqualTo character.map { it.toCharacterDetailDomain() }
                }
            }
        }
    }
}