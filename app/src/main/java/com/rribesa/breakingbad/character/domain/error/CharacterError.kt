package com.rribesa.breakingbad.character.domain.error

sealed class CharacterError(override val message: String?) : Throwable(message) {
    data class GenericError(override val message: String?, val originalCause: Throwable) :
        CharacterError(message)

    data class RemoteError(
        override val message: String?,
        val code: Int,
        val originalCause: Throwable
    ) : CharacterError(message)

    data class LocalError(override val message: String?, val originalCause: Throwable) :
        CharacterError(message)

    data class EmptyError (
        override val message: String? = "Empty List") : CharacterError(message)
}
