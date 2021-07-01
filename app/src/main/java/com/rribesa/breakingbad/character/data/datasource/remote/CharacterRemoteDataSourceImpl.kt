package com.rribesa.breakingbad.character.data.datasource.remote

import android.util.Log
import com.rribesa.breakingbad.character.data.service.CharacterAPI
import com.rribesa.breakingbad.character.data.service.response.CharacterResponse
import com.rribesa.breakingbad.character.domain.error.CharacterError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

class CharacterRemoteDataSourceImpl(
    private val api: CharacterAPI,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CharacterRemoteDataSource {
    override suspend fun getRandom(): Flow<CharacterResponse> =
        flow {
            emit(api.getRandom())
        }.flowOn(dispatcher)
            .catch { error ->
                throw (handleError(error))
            }


    override suspend fun getAll(): Flow<List<CharacterResponse>> {
        return flowOf(api.getAll())
            .flowOn(dispatcher)
            .catch { error ->
                throw (handleError(error))
            }
    }


    override suspend fun get(name: String): Flow<CharacterResponse> =
        flow {
            emit(api.get(name))
        }.flowOn(dispatcher)
            .catch { error ->
                throw (handleError(error))
            }
}

private fun handleError(error: Throwable): Throwable {
    Log.e("RENATO", error.message.toString())
    print("CAUSE: " + error.cause + "MESSAGE: " + error.message)
    if (error is HttpException) {
        return CharacterError.RemoteError(
            code = error.code(),
            message = error.message(),
            originalCause = error
        )
    }
    return CharacterError.GenericError(message = error.message, originalCause = error)
}