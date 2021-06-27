package com.mjb.mymarvelapp.characters.usecase

import com.mjb.characters.data.service.CharactersService
import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.data.model.data.CharacterDetail
import com.mjb.characters.domain.usecase.GetCharacterDetailUseCase
import com.mjb.mymarvelapp.core.network.NetworkHandler
import com.mjb.core.utils.State
import com.mjb.core.utils.Success
import com.mjb.mymarvelapp.utils.mockApiResponse
import com.mjb.mymarvelapp.utils.mockCharacters
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetCharacterDetailUseCaseTest {

    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase
    private var repository = mock<CharactersRepositoryImpl>()
    private var service = mock<CharactersService>()
    private var networkHandler = mock<NetworkHandler>()
    private val characterId = 0
    private val character = mockCharacters(1)
    private val mockResponse = mockApiResponse(character)

    @Before
    fun setup() {
        service = mock {
            onBlocking { getCharacterDetail(characterId) } doReturn Response.success(mockResponse)
        }

        networkHandler = mock {
            onBlocking { isConnected } doReturn true
        }

        repository = CharactersRepositoryImpl(service, networkHandler)
        getCharacterDetailUseCase = GetCharacterDetailUseCase(repository)
    }

    @Test
    fun `should get all characters on success`() = runBlocking {
        // GIVEN
        // WHEN
        val flow: Flow<State<List<CharacterDetail>>> =
            getCharacterDetailUseCase.run(GetCharacterDetailUseCase.Params(characterId))

        // THEN
        flow.collect { result ->
            result.`should be instance of`<Success<List<CharacterDetail>>>()
            when (result) {
                is Success<List<CharacterDetail>> -> {
                    result.data shouldBeEqualTo character.map {
                        it.toCharacterDetailDomain()
                    }
                }
            }
        }
    }
}