package com.rribesa.breakingbad.character.data.datasource.remote

import com.rribesa.breakingbad.character.data.service.response.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface CharacterRemoteDataSource {

    suspend fun getRandom(): Flow<CharacterResponse>

    suspend fun getAll(): Flow<List<CharacterResponse>>

    suspend fun get(name: String): Flow<CharacterResponse>
}