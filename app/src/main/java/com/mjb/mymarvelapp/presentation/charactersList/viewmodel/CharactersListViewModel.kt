package com.mjb.mymarvelapp.presentation.charactersList.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mjb.mymarvelapp.domain.model.CharacterList
import com.mjb.mymarvelapp.domain.usecase.GetCharactersListUseCase
import com.mjb.mymarvelapp.presentation.base.BaseViewModel
import com.mjb.mymarvelapp.presentation.charactersList.models.CharacterListView
import com.mjb.mymarvelapp.infrastructure.utils.BadRequest
import com.mjb.mymarvelapp.infrastructure.utils.Error
import com.mjb.mymarvelapp.infrastructure.utils.ErrorNoConnection
import com.mjb.mymarvelapp.infrastructure.utils.Success
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(val getCharactersListUseCase: GetCharactersListUseCase) :
    BaseViewModel() {

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
                        is Success<List<CharacterList>> -> handleSuccessGetCharactersList(
                            result.data
                        )
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
                        is Success<List<CharacterList>> -> handleSuccessGetMoreCharacters(
                            result.data
                        )
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
