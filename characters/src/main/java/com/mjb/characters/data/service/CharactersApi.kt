package com.mjb.characters.data.service

import com.mjb.characters.data.model.api.response.ApiResponse
import com.mjb.characters.data.model.api.response.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    companion object {
        private const val CHARACTERS_ENDPOINT = "characters"
    }

    @GET(CHARACTERS_ENDPOINT)
    suspend fun getCharactersList(@Query("offset") offset: Int): Response<ApiResponse<CharacterResponse>>

    @GET(CHARACTERS_ENDPOINT.plus("/{character_id}"))
    suspend fun getCharacterDetail(@Path("character_id") characterId: Int): Response<ApiResponse<CharacterResponse>>
}
