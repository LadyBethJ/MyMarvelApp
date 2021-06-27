package com.mjb.characters.data.service

import com.mjb.characters.data.model.api.response.ApiResponse
import com.mjb.characters.data.model.api.response.CharacterResponse
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class CharactersService @Inject constructor(retrofit: Retrofit) : CharactersApi {

    private val charactersApi by lazy { retrofit.create(CharactersApi::class.java) }

    override suspend fun getCharactersList(offset: Int): Response<ApiResponse<CharacterResponse>> =
        charactersApi.getCharactersList(offset)

    override suspend fun getCharacterDetail(characterId: Int): Response<ApiResponse<CharacterResponse>> =
        charactersApi.getCharacterDetail(characterId)
}
