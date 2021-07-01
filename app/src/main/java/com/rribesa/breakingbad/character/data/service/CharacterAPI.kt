package com.rribesa.breakingbad.character.data.service

import com.rribesa.breakingbad.character.data.service.response.CharacterResponse
import retrofit2.http.GET

interface CharacterAPI {

    suspend fun getRandom(): CharacterResponse

    @GET("characters")
    suspend fun getAll(): List<CharacterResponse>

    suspend fun get(name: String): CharacterResponse

}