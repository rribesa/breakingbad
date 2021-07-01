package com.rribesa.breakingbad.character.data.datasource.local

import com.rribesa.breakingbad.character.data.dao.CharacterDAO
import com.rribesa.breakingbad.character.data.entity.CharacterEntity
import com.rribesa.breakingbad.character.domain.error.CharacterError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CharacterLocalDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dao: CharacterDAO

) : CharacterLocalDataSource {

    override suspend fun getAll(): Flow<List<CharacterEntity>> =
        dao.getAll().flowOn(dispatcher).catch {
            throw CharacterError.LocalError(it.message, it)
        }

    override suspend fun get(name: String): Flow<List<CharacterEntity>> =
        dao.get(name).flowOn(dispatcher).catch {
            throw CharacterError.LocalError(it.message, it)
        }

    override suspend fun save(character: CharacterEntity) = withContext(dispatcher) {
        dao.insert(character)
    }

    override suspend fun save(entityList: List<CharacterEntity>) = withContext(dispatcher) {
        dao.deleteAndInsertAll(entityList)
    }

}