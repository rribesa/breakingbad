package com.rribesa.breakingbad.character.data.repository

import com.rribesa.breakingbad.character.data.datasource.local.CharacterLocalDataSource
import com.rribesa.breakingbad.character.data.datasource.remote.CharacterRemoteDataSource
import com.rribesa.breakingbad.character.data.entity.toCharacter
import com.rribesa.breakingbad.character.data.service.response.CharacterResponse
import com.rribesa.breakingbad.character.data.service.response.toCharacter
import com.rribesa.breakingbad.character.data.service.response.toEntity
import com.rribesa.breakingbad.character.domain.model.Character
import com.rribesa.breakingbad.character.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class CharacterRepositoryImpl(
    private val local: CharacterLocalDataSource,
    private val remote: CharacterRemoteDataSource
) : CharacterRepository {
    override suspend fun getAll(): Flow<List<Character>> =
        remote.getAll()
            .onEach {
                save(it)
            }.catch {
                getCachedList(it)
            }.map { list ->
                list.map { it.toCharacter() }
            }


    private suspend fun getCachedList(remoteError: Throwable? = null): Flow<List<Character>> =
        local.getAll()
            .map { entityList ->
                entityList.map { it.toCharacter() }
            }.catch { localError ->
                remoteError?.let {
                    throw remoteError
                } ?: throw localError
            }


    override suspend fun get(name: String): Flow<Character> =
        remote.get(name)
            .catch { Result.failure<Character>(it) }
            .map {
                it.toCharacter()
            }

    override suspend fun getRandom(): Flow<Character> =
        remote.getRandom().map {
            it.toCharacter()

        }

    private suspend fun save(list: List<CharacterResponse>) {
        if (list.isNotEmpty()) {
            local.save(
                list.map {
                    it.toEntity()
                }
            )
        }
    }
}

