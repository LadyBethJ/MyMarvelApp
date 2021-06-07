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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
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
                .onEach { handleShowSpinner(false) }
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

    private fun handleSuccessGetCharactersList(data: List<CharacterList>) {
        charactersList = data.map { it.toCharacterView() }.toMutableList()
        charactersResponse.postValue(charactersList)
    }

    private fun handleSuccessGetMoreCharacters(data: List<CharacterList>) {
        charactersList.addAll(data.map { it.toCharacterView() })
        moreCharactersResponse.postValue(charactersList)
    }

    fun filterCharactersList(text: String) {
        val filteredList = charactersList.filter { character ->
            containsText(character, text.toLowerCase(Locale.getDefault()))
        }
        charactersResponse.postValue(filteredList)
    }

    private fun containsText(character: CharacterListView, text: String): Boolean {
        return character.name.toLowerCase(Locale.getDefault()).contains(text)
    }
}
