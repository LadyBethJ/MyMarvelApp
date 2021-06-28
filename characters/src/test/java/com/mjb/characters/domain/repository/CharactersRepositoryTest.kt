package com.mjb.characters.domain.repository

import com.mjb.characters.data.service.CharactersService
import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.data.model.data.CharacterDetail
import com.mjb.characters.data.model.data.CharacterList
import com.mjb.core.network.NetworkHandler
import com.mjb.core.utils.State
import com.mjb.core.utils.Success
import com.mjb.characters.utils.mockApiResponse
import com.mjb.characters.utils.mockCharacters
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import retrofit2.Response

class CharactersRepositoryTest {

    @Test
    fun `should get a list of characters from service on success`(): Unit = runBlocking {
        // GIVEN
        val charactersList = mockCharacters(10)
        val mockResponse = mockApiResponse(charactersList)
        val offset = 0

        // WHEN
        val service = mock<CharactersService> {
            onBlocking { getCharactersList(offset) } doReturn Response.success(mockResponse)
        }
        service.getCharactersList(offset).body() shouldBeEqualTo mockResponse

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }
        val repository = CharactersRepositoryImpl(
            service,
            networkHandler
        )
        val flow: Flow<State<List<CharacterList>>> =
            repository.getCharactersList(offset)

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

    @Test
    fun `should get a characters from service on success`(): Unit = runBlocking {
        // GIVEN
        val character = mockCharacters(1)
        val mockResponse = mockApiResponse(character)
        val characterId = 0

        // WHEN
        val service = mock<CharactersService> {
            onBlocking { getCharacterDetail(characterId) } doReturn Response.success(mockResponse)
        }
        service.getCharacterDetail(characterId).body() shouldBeEqualTo mockResponse

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }
        val repository = CharactersRepositoryImpl(
            service,
            networkHandler
        )
        val flow: Flow<State<List<CharacterDetail>>> =
            repository.getCharacterDetail(characterId)

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