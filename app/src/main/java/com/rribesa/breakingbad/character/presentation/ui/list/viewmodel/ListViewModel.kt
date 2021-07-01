package com.rribesa.breakingbad.character.presentation.ui.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rribesa.breakingbad.character.domain.error.CharacterError
import com.rribesa.breakingbad.character.domain.model.Character
import com.rribesa.breakingbad.character.domain.repository.CharacterRepository
import com.rribesa.breakingbad.character.presentation.ui.list.viewmodel.action.ListViewAction
import com.rribesa.breakingbad.character.presentation.ui.list.viewmodel.state.ListViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _state: MutableLiveData<ListViewState> = MutableLiveData()
    val state: LiveData<ListViewState> = _state
    private val _action: MutableLiveData<ListViewAction> = MutableLiveData()
    val action: LiveData<ListViewAction> = _action

    fun fetchList() {
        viewModelScope.launch {
            repository.getAll()
                .onStart { _state.postValue(_state.value?.startLoading()) }
                .catch { error -> handleError(error) }
                .take(1)
                .collect { result ->

                    _state.postValue(
                        _state.value?.fillList(result)
                    )
                }

        }
    }


    fun chooseCharacter(character: Character) {
        _action.postValue(ListViewAction.NavigateDetailCharacter(character))
    }

    private fun handleError(error: Throwable) {
        print(error)
        when (error) {
            is CharacterError.GenericError -> navigateGenericError(error)
            is CharacterError.RemoteError -> navigateGenericError(error)
            is CharacterError.EmptyError -> navigateGenericError(error)
            is CharacterError.LocalError -> navigateGenericError(error)
            else -> navigateGenericError(error)
        }
    }

    private fun navigateGenericError(error: Throwable) {
        _state.postValue(_state.value?.stopLoading())
        _action.postValue(ListViewAction.GenericError(error))

    }
}

