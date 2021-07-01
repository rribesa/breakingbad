package com.rribesa.breakingbad.character.domain.usecase

import com.rribesa.breakingbad.character.domain.error.CharacterError
import com.rribesa.breakingbad.character.domain.model.Character
import com.rribesa.breakingbad.character.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterGetAllUseCase(private val repository: CharacterRepository) {
    suspend operator fun invoke(): Flow<List<Character>> = repository.getAll().map {
        if (it.isEmpty()) {
            throw CharacterError.EmptyError()
        } else {
            it
        }
    }
}

