package com.mjb.characters.domain.usecase

import com.mjb.characters.data.service.CharactersService
import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.data.model.data.CharacterList
import com.mjb.characters.domain.repository.CharactersRepository
import com.mjb.core.utils.State
import com.mjb.core.utils.Success
import com.mjb.characters.utils.mockApiResponse
import com.mjb.characters.utils.mockCharacters
import com.mjb.core.network.NetworkHandler
import com.nhaarman.mockitokotlin2.doReturn
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

    @Test
    fun `should get all characters on success`() = runBlocking {
        // GIVEN
        val charactersList = mockCharacters(10)
        val mockResponse = mockApiResponse(charactersList)
        val offset = 0

        // WHEN
        val service = mock<CharactersService> {
            onBlocking { getCharactersList(offset) } doReturn Response.success(mockResponse)
        }
        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }
        val repository = CharactersRepositoryImpl(
            service,
            networkHandler
        )

        getCharactersListUseCase = GetCharactersListUseCase(repository)

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