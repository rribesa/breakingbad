package com.rribesa.breakingbad.character.presentation.ui.list.viewmodel.action

import com.rribesa.breakingbad.character.domain.model.Character

sealed class ListViewAction {
    data class NavigateDetailCharacter(val character: Character) : ListViewAction()
    data class GenericError(val error:Throwable):ListViewAction()
}
