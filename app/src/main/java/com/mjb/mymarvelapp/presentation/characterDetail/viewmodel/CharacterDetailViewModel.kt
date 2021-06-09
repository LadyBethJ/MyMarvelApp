package com.mjb.mymarvelapp.presentation.characterDetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mjb.mymarvelapp.domain.model.CharacterDetail
import com.mjb.mymarvelapp.domain.usecase.GetCharacterDetailUseCase
import com.mjb.mymarvelapp.presentation.base.BaseViewModel
import com.mjb.mymarvelapp.presentation.characterDetail.models.CharacterDetailView
import com.mjb.mymarvelapp.infrastructure.utils.BadRequest
import com.mjb.mymarvelapp.infrastructure.utils.Error
import com.mjb.mymarvelapp.infrastructure.utils.ErrorNoConnection
import com.mjb.mymarvelapp.infrastructure.utils.Success
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : BaseViewModel() {

    lateinit var characterDetail: CharacterDetailView
    var characterDetailResponse = MutableLiveData<CharacterDetailView>()

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            getCharacterDetailUseCase(GetCharacterDetailUseCase.Params(characterId))
                .onStart { handleShowSpinner(true) }
                .onCompletion { handleShowSpinner(false) }
                .catch { failure -> handleFailure(failure) }
                .collect { result ->
                    when (result) {
                        is Success<List<CharacterDetail>> -> handleSuccessGetCharacterDetail(result.data)
                        is Error -> handleFailure(result.exception)
                        is ErrorNoConnection -> handleFailure(result.exception)
                        is BadRequest -> handleFailure(result.exception)
                    }
                }
        }
    }

    private fun handleSuccessGetCharacterDetail(data: List<CharacterDetail>?) {
        if (data?.get(0) != null) {
            characterDetail = data[0].toCharacterDetailView()
            characterDetailResponse.postValue(characterDetail)
        }
    }

}
