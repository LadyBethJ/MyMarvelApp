package com.mjb.mymarvelapp.characters.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mjb.mymarvelapp.data.repository.CharactersRepositoryImpl
import com.mjb.mymarvelapp.domain.model.CharacterDetail
import com.mjb.mymarvelapp.domain.usecase.GetCharacterDetailUseCase
import com.mjb.mymarvelapp.infrastructure.utils.Error
import com.mjb.mymarvelapp.infrastructure.utils.Success
import com.mjb.mymarvelapp.presentation.characterDetail.models.CharacterDetailView
import com.mjb.mymarvelapp.presentation.characterDetail.viewmodel.CharacterDetailViewModel
import com.mjb.mymarvelapp.utils.CoroutineTestRule
import com.mjb.mymarvelapp.utils.mockCharacters
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase
    private var repository = mock<CharactersRepositoryImpl>()
    private val characterObserver = mock<Observer<CharacterDetailView>>()
    private val isErrorObserver = mock<Observer<Throwable>>()
    private val characterId = 0

    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        getCharacterDetailUseCase = GetCharacterDetailUseCase(repository)
        viewModel = CharacterDetailViewModel(getCharacterDetailUseCase).apply {
            characterDetailResponse.observeForever(characterObserver)
            failure.observeForever(isErrorObserver)
        }
    }

    @Test
    fun `should emit get character detail on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            // GIVEN
            val charactersList =
                Success(mockCharacters(1).map { it.toCharacterDetailDomain() })

            val channel = Channel<Success<List<CharacterDetail>>>()
            val flow = channel.consumeAsFlow()

            // WHEN
            doReturn(flow)
                .whenever(repository)
                .getCharacterDetail(characterId)

            launch {
                channel.send(charactersList)
                channel.close(IOException())
            }

            viewModel.getCharacterDetail(characterId)

            // THEN
            verify(characterObserver).onChanged(
                charactersList.data?.map { it.toCharacterDetailView() }?.get(0)
            )
        }

    @Test
    fun `should emit error on get character detail failure`() =
        coroutinesRule.dispatcher.runBlockingTest {
            // GIVEN
            val charactersList =
                Success(mockCharacters(1).map { it.toCharacterDetailDomain() })
            val expectedError = Error(Throwable())

            val channel = Channel<Success<List<CharacterDetail>>>()
            val flow = channel.consumeAsFlow()

            // WHEN
            doReturn(flow)
                .whenever(repository)
                .getCharacterDetail(characterId)

            launch {
                channel.send(charactersList)
                channel.close(expectedError.exception)
            }

            viewModel.getCharacterDetail(characterId)

            // THEN
            verify(characterObserver).onChanged(
                charactersList.data?.map { it.toCharacterDetailView() }?.get(0)
            )
            verify(isErrorObserver).onChanged(expectedError.exception)
        }
}