package com.rribesa.breakingbad.character.data.datasource.local

import com.rribesa.breakingbad.character.data.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharacterLocalDataSource {

    suspend fun getAll(): Flow<List<CharacterEntity>>

    suspend fun get(name: String): Flow<List<CharacterEntity>>

    suspend fun save(character: CharacterEntity)

    suspend fun save(entityList: List<CharacterEntity>)
}
