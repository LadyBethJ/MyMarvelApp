package com.mjb.mymarvelapp.presentation.charactersList.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mjb.mymarvelapp.databinding.FragmentCharactersListBinding
import com.mjb.mymarvelapp.core.di.component.ViewComponent
import com.mjb.mymarvelapp.presentation.base.BaseFragment
import com.mjb.mymarvelapp.presentation.charactersList.adapters.CharactersListAdapter
import com.mjb.characters.data.model.view.CharacterListView
import com.mjb.core.extensions.failure
import com.mjb.core.extensions.infiniteScroll
import com.mjb.core.extensions.observe
import com.mjb.mymarvelapp.presentation.charactersList.viewmodel.CharactersListViewModel
import javax.inject.Inject

class CharactersListFragment : BaseFragment() {

    @Inject
    lateinit var charactersListViewModel: CharactersListViewModel

    override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!
    private val adapter = CharactersListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(charactersListViewModel) {
            observe(showSpinner, ::showSpinner)
            failure(failure, ::handleFailure)
            failure(badRequest, ::handleFailure)
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
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.infiniteScroll {
                charactersListViewModel.charactersListScrolled()
            }
            recyclerView.adapter = adapter
            if (adapter.itemCount == 0) {
                charactersListViewModel.getCharactersList()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        binding.apply {
            recyclerView.visibility = View.GONE
            showAlertDialog(failure)
        }
    }
}
