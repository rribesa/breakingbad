package com.rribesa.breakingbad.character.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rribesa.breakingbad.R
import com.rribesa.breakingbad.character.domain.model.Character
import com.rribesa.breakingbad.character.presentation.ui.list.viewmodel.ListViewModel
import com.rribesa.breakingbad.character.presentation.ui.list.viewmodel.action.ListViewAction
import com.rribesa.breakingbad.databinding.FragmentCharacterListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListFragment : Fragment() {
    private val listViewModel: ListViewModel by viewModel()
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: CharacterListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        observeAction()
        listViewModel.fetchList()
    }

    private fun observeState() {
        listViewModel.state.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.let { state ->
                binding.listProgressBar.isVisible = state.showLoading
                binding.characterListRecycle.isVisible = state.listIsVisible
                fillList(state.list)
            }
        })
    }

    private fun fillList(list: List<Character>) {
        this.adapter = CharacterListAdapter(
            characters = list,
            adapterOnClick = { character ->
                listViewModel.chooseCharacter(character)
            }
        )
        binding.characterListRecycle.layoutManager = LinearLayoutManager(this.requireContext())
        binding.characterListRecycle.adapter = adapter
    }

    private fun observeAction() {
        listViewModel.action.observe(viewLifecycleOwner, { viewAction ->
            viewAction?.let { action ->
                when (action) {
                    is ListViewAction.NavigateDetailCharacter -> navigateCharacter(action.character)
                    is ListViewAction.GenericError -> navigateError(action.error)
                }
            }
        })
    }

    private fun navigateCharacter(character: Character) {
        findNavController().navigate(R.id.navigation_detail)
    }

    private fun navigateError(error: Throwable) {
        println(error.message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}