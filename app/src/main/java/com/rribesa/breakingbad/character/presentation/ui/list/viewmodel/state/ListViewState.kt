package com.rribesa.breakingbad.character.presentation.ui.list.viewmodel.state

import com.rribesa.breakingbad.character.domain.model.Character

data class ListViewState(
    val showLoading: Boolean = false,
    val listIsVisible: Boolean = false,
    val list: List<Character> = emptyList()
) {
    fun fillList(list: List<Character>) = this.copy(list = list, listIsVisible = true)
    fun startLoading() = this.copy(showLoading = true, listIsVisible = false)
    fun stopLoading() = this.copy(showLoading = false)
}
