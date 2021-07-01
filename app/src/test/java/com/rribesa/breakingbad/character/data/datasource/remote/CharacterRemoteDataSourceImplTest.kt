package com.rribesa.breakingbad.character.data.datasource.remote

import com.rribesa.breakingbad.character.data.service.CharacterAPI
import com.rribesa.breakingbad.character.data.service.response.CharacterResponse
import com.rribesa.breakingbad.character.domain.error.CharacterError
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class CharacterRemoteDataSourceImplTest {
    private val api: CharacterAPI = mockk()
    private val errorMessage = "test"
    private val errorCode = 400
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val dataSource: CharacterRemoteDataSource =
        CharacterRemoteDataSourceImpl(api, coroutineDispatcher)

    private val characterMock = CharacterResponse(
        birthday = "1990-03-06",
        charId = 1,
        img = "",
        name = "Renato",
        nickname = "Santos",
        occupation = listOf("System Analyst"),
        portrayed = "Renato Ribeiro",
        status = "live",
        category = "",
    )

    @Test
    fun `getRandom should collect CharacterResponse when CharacterApi call random with success`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val character = characterMock
            coEvery { api.getRandom() }.returns(character)

            // When
            val result = dataSource.getRandom()

            //Then
            result.collect {
                assertEquals(character, it)
            }
        }

    @Test
    fun `getRandom should catch NetworkError when CharacterApi call random with NetworkException`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val error: HttpException = mockk()
            every { error.code() } returns errorCode
            every { error.message() } returns errorMessage
            coEvery { api.getRandom() } throws error

            // When
            val result = dataSource.getRandom()

            //Then
            val expected = CharacterError.RemoteError(error.message(), error.code(), error)
            result.catch {
                assertEquals(expected, it)
            }.collect()
        }

    @Test
    fun `getRandom should catch Generic when CharacterApi call random with Throwable`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val error = Throwable()
            coEvery { api.getRandom() } throws error

            // When
            val result = dataSource.getRandom()

            //Then
            val expected = CharacterError.GenericError(error.message, error)
            result.catch {
                assertEquals(expected, it)
            }.collect()
        }

    @Test
    fun `get should collect CharacterResponse when CharacterApi call get with success`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val name = characterMock.name
            val character = characterMock
            coEvery { api.get(name) }.returns(character)

            // When
            val result = dataSource.get(name)

            //Then
            result.collect {
                assertEquals(character, it)
            }
        }

    @Test
    fun `get should catch NetworkError when CharacterApi call get with NetworkException`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val name = characterMock.name
            val error: HttpException = mockk()
            every { error.code() } returns errorCode
            every { error.message() } returns errorMessage
            coEvery { api.get(name) } throws error

            // When
            val result = dataSource.get(name)

            //Then
            val expected = CharacterError.RemoteError(error.message(), error.code(), error)
            result.catch {
                assertEquals(expected, it)
            }.collect()
        }

    @Test
    fun `get should catch Generic when CharacterApi call get with Throwable`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val name = characterMock.name
            val error = Throwable()
            coEvery { api.get(name) } throws error

            // When
            val result = dataSource.get(name)

            //Then
            //Then
            val expected = CharacterError.GenericError(error.message, error)
            result.catch {
                assertEquals(expected, it)
            }.collect()
        }

    @Test
    fun `getAll should collect List CharacterResponse when CharacterApi call getAll with success`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val characterList = listOf(characterMock)
            coEvery { api.getAll() }.returns(characterList)

            // When
            val result = dataSource.getAll()

            //Then
            result.collect {
                assertEquals(characterList, it)
            }
        }

    @Test
    fun `getAll should catch NetworkError when CharacterApi call getAll with NetworkException`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val error: HttpException = mockk()
            every { error.code() } returns errorCode
            every { error.message() } returns errorMessage
            coEvery { api.getAll() } throws error

            // When
            val result = dataSource.getAll()

            //Then
            val expected = CharacterError.RemoteError(error.message(), error.code(), error)
            result.catch {
                assertEquals(expected, it)
            }.collect()
        }

    @Test
    fun `getAll should catch Generic when CharacterApi call getAll with Throwable`() =
        coroutineDispatcher.runBlockingTest {
            // Given
            val error = Throwable()
            coEvery { api.getAll() } throws error

            // When
            val result = dataSource.getAll()

            //Then
            val expected = CharacterError.GenericError(error.message, error)
            result.catch {
                assertEquals(expected, it)
            }.collect()
        }
}
