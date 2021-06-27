package com.mjb.mymarvelapp.characters.usecase

import com.mjb.characters.data.service.CharactersService
import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.data.model.data.CharacterList
import com.mjb.characters.domain.usecase.GetCharactersListUseCase
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

class GetCharactersListUseCaseTest {

    private lateinit var getCharactersListUseCase: GetCharactersListUseCase
    private var repository = mock<CharactersRepositoryImpl>()
    private var service = mock<CharactersService>()
    private var networkHandler = mock<NetworkHandler>()
    private val offset = 0
    private val charactersList = mockCharacters(10)
    private val mockResponse = mockApiResponse(charactersList)

    @Before
    fun setup() {
        service = mock {
            onBlocking { getCharactersList(offset) } doReturn Response.success(mockResponse)
        }

        networkHandler = mock {
            onBlocking { isConnected } doReturn true
        }

        repository = CharactersRepositoryImpl(service, networkHandler)
        getCharactersListUseCase = GetCharactersListUseCase(repository)
    }

    @Test
    fun `should get all characters on success`() = runBlocking {
        // GIVEN
        // WHEN
        val flow: Flow<State<List<CharacterList>>> =
            getCharactersListUseCase.run(GetCharactersListUseCase.Params(offset))

        // THEN
        flow.collect { result ->
            result.`should be instance of`<Success<List<CharacterList>>>()
            when (result) {
                is Success<List<CharacterList>> -> {
                    result.data shouldBeEqualTo charactersList.map { it.toCharacterListDomain() }
                }
            }
        }
    }
}