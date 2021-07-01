package com.rribesa.breakingbad.character.domain.usecase

import com.rribesa.breakingbad.character.domain.repository.CharacterRepository

class CharacterGetRandomUseCase(private val repository: CharacterRepository) {
    suspend operator fun invoke() = repository.getRandom()
}