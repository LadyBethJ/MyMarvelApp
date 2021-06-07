package com.mjb.mymarvelapp.presentation.charactersList.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mjb.mymarvelapp.databinding.FragmentCharactersListBinding
import com.mjb.mymarvelapp.infrastructure.di.component.ViewComponent
import com.mjb.mymarvelapp.presentation.base.BaseFragment
import com.mjb.mymarvelapp.presentation.charactersList.adapters.CharactersListAdapter
import com.mjb.mymarvelapp.presentation.charactersList.models.CharacterListView
import com.mjb.mymarvelapp.presentation.charactersList.viewmodel.CharactersListViewModel
import com.mjb.mymarvelapp.infrastructure.exception.ErrorHandler
import com.mjb.mymarvelapp.presentation.utils.extensions.failure
import com.mjb.mymarvelapp.presentation.utils.extensions.observe
import javax.inject.Inject

class CharactersListFragment : BaseFragment() {

    @Inject
    lateinit var charactersListViewModel: CharactersListViewModel

    override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)

    private lateinit var binding: FragmentCharactersListBinding
    private val adapter = CharactersListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(charactersListViewModel) {
            observe(showSpinner, ::showSpinner)
            failure(failure, ::handleFailure)
            failure(badRequest, ::handleBadRequest)
            observe(charactersResponse, ::setListOfCharacters)
            observe(moreCharactersResponse, ::addMoreCharacters)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.adapter = adapter
            if (adapter.itemCount == 0) {
                charactersListViewModel.getCharactersList()
            }
        }
    }

    private fun setListOfCharacters(characters: List<CharacterListView>?) {
        binding.apply {
            recyclerView.visibility = View.VISIBLE
            adapter.submitList(characters ?: emptyList())
        }
    }

    private fun addMoreCharacters(characters: List<CharacterListView>?) {
        adapter.submitList(characters ?: emptyList())
    }

    private fun handleFailure(failure: Throwable?) {
        if (failure?.message == ErrorHandler.NETWORK_ERROR_MESSAGE) {
            binding.apply {
                recyclerView.visibility = View.GONE
            }
        }
    }

    private fun handleBadRequest(failure: Throwable?) {
        binding.apply {
            recyclerView.visibility = View.GONE
        }
    }
}
