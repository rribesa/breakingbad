package com.rribesa.breakingbad.character.data.di

import androidx.room.Room
import com.rribesa.breakingbad.Constants.DataBase.DATABASE_NAME
import com.rribesa.breakingbad.Constants.Service.BASE_URL
import com.rribesa.breakingbad.character.data.dao.CharacterDAO
import com.rribesa.breakingbad.character.data.database.CharacterDataBase
import com.rribesa.breakingbad.character.data.datasource.local.CharacterLocalDataSource
import com.rribesa.breakingbad.character.data.datasource.local.CharacterLocalDataSourceImpl
import com.rribesa.breakingbad.character.data.datasource.remote.CharacterRemoteDataSource
import com.rribesa.breakingbad.character.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.rribesa.breakingbad.character.data.repository.CharacterRepositoryImpl
import com.rribesa.breakingbad.character.data.service.CharacterAPI
import com.rribesa.breakingbad.character.domain.repository.CharacterRepository
import com.rribesa.breakingbad.character.domain.usecase.CharacterGetAllUseCase
import com.rribesa.breakingbad.character.domain.usecase.CharacterGetRandomUseCase
import com.rribesa.breakingbad.character.domain.usecase.CharacterGetUseCase
import com.rribesa.breakingbad.character.presentation.ui.list.viewmodel.ListViewModel
import com.samples.network.WebClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("EXPERIMENTAL_API_USAGE")
object CharacterDataDI {
    // Data
    val characterModule = module {
        single {
            WebClient.service<CharacterAPI>(BASE_URL)
        }
        single {
            Room.databaseBuilder(
                get(),
                CharacterDataBase::class.java,
                DATABASE_NAME
            )
                .build()
        }
        single<CharacterDAO> { get<CharacterDataBase>().characterDAO() }
        factory<CharacterRemoteDataSource> { CharacterRemoteDataSourceImpl(api = get()) }
        factory<CharacterLocalDataSource> { CharacterLocalDataSourceImpl(dao = get()) }
        factory<CharacterRepository> { CharacterRepositoryImpl(remote = get(), local = get()) }

        // Domain
        factory { CharacterGetAllUseCase(repository = get()) }
        factory { CharacterGetRandomUseCase(repository = get()) }
        factory { CharacterGetUseCase(repository = get()) }


        // Presentation
        viewModel {
            ListViewModel(
                repository = get(),
            )
        }
    }
}