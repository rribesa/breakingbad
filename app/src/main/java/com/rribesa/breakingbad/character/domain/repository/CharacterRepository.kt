package com.rribesa.breakingbad.character.domain.repository

import com.rribesa.breakingbad.character.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getAll(): Flow<List<Character>>

    suspend fun get(name: String): Flow<Character>

    suspend fun getRandom(): Flow<Character>
}