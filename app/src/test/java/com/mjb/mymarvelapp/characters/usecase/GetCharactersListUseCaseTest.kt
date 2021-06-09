package com.mjb.mymarvelapp.characters.usecase

import com.mjb.mymarvelapp.data.datasource.api.service.CharactersService
import com.mjb.mymarvelapp.data.repository.CharactersRepositoryImpl
import com.mjb.mymarvelapp.domain.model.CharacterList
import com.mjb.mymarvelapp.domain.usecase.GetCharactersListUseCase
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

class GetCharactersListUseCaseTest {

    @Test
    fun `should get all characters on success`() = runBlocking {
        // GIVEN
        val repository: CharactersRepositoryImpl
        val offset = 0
        val charactersList = mockCharacters(10)
        val mockResponse = mockApiResponse(charactersList)

        // WHEN
        val service = mock<CharactersService> {
            onBlocking { getCharactersList(offset) } doReturn Response.success(mockResponse)
        }

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }

        repository = CharactersRepositoryImpl(service, networkHandler)
        val getCharactersListUseCase: GetCharactersListUseCase = GetCharactersListUseCase(repository)

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