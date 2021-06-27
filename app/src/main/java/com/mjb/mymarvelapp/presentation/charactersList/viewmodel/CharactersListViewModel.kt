package com.mjb.mymarvelapp.presentation.charactersList.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mjb.characters.data.model.data.CharacterList
import com.mjb.characters.domain.usecase.GetCharactersListUseCase
import com.mjb.mymarvelapp.presentation.base.BaseViewModel
import com.mjb.characters.data.model.view.CharacterListView
import com.mjb.core.utils.BadRequest
import com.mjb.core.utils.Error
import com.mjb.core.utils.ErrorNoConnection
import com.mjb.core.utils.Success
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
//import javax.inject.Inject

//TODO borrar dagger
//class CharactersListViewModel @Inject constructor(val getCharactersListUseCase: GetCharactersListUseCase) :
class CharactersListViewModel (
    val getCharactersListUseCase: GetCharactersListUseCase
) : BaseViewModel() {

    companion object {
        private const val MAX_OFFSET = 20
    }

    private var lastPageCount: Int = 0

    var charactersList: MutableList<CharacterListView> = mutableListOf()
    var charactersResponse = MutableLiveData<List<CharacterListView>>()
    var moreCharactersResponse = MutableLiveData<List<CharacterListView>>()

    fun getCharactersList() {
        viewModelScope.launch {
            getCharactersListUseCase(GetCharactersListUseCase.Params(lastPageCount))
                .onStart { handleShowSpinner(true) }
                .onCompletion { handleShowSpinner(false) }
                .catch { failure -> handleFailure(failure) }.collect { result ->
                    when (result) {
                        is Success<List<CharacterList>> ->
                            result.data?.let {
                                handleSuccessGetCharactersList(it)
                            }
                        is Error -> handleFailure(result.exception)
                        is ErrorNoConnection -> handleFailure(result.exception)
                        is BadRequest -> handleBadRequest(result.exception)
                    }
                }
        }
    }

    fun charactersListScrolled() {
        lastPageCount += MAX_OFFSET
        viewModelScope.launch {
            getCharactersListUseCase(GetCharactersListUseCase.Params(lastPageCount))
                .catch { failure -> handleFailure(failure) }
                .collect { result ->
                    when (result) {
                        is Success<List<CharacterList>> ->
                            result.data?.let {
                                handleSuccessGetMoreCharacters(it)
                            }
                        is Error -> handleFailure(result.exception)
                        is ErrorNoConnection -> handleFailure(result.exception)
                        is BadRequest -> handleFailure(result.exception)
                    }
                }
        }
    }

    private fun handleSuccessGetCharactersList(data: List<CharacterList>) {
        charactersList = data.map { it.toCharacterView() }.toMutableList()
        charactersResponse.postValue(charactersList)
    }

    private fun handleSuccessGetMoreCharacters(data: List<CharacterList>) {
        charactersList.addAll(data.map { it.toCharacterView() })
        moreCharactersResponse.postValue(charactersList)
    }
}
