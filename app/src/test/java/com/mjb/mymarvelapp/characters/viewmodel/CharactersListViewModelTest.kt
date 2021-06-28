package com.mjb.mymarvelapp.characters.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.data.model.data.CharacterList
import com.mjb.characters.domain.usecase.GetCharactersListUseCase
import com.mjb.core.utils.Error
import com.mjb.core.utils.Success
import com.mjb.characters.data.model.view.CharacterListView
import com.mjb.mymarvelapp.presentation.charactersList.viewmodel.CharactersListViewModel
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

class CharactersListViewModelTest {

    private lateinit var viewModel: CharactersListViewModel
    private lateinit var getCharactersListUseCase: GetCharactersListUseCase

    private var repository = mock<CharactersRepositoryImpl>()
    private val charactersObserver = mock<Observer<List<CharacterListView>>>()
    private val moreCharactersObserver = mock<Observer<List<CharacterListView>>>()
    private val isErrorObserver = mock<Observer<Throwable>>()
    private var initialOffset = 0
    private var finalOffset = 20

    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        getCharactersListUseCase = GetCharactersListUseCase(repository)
        viewModel = CharactersListViewModel(getCharactersListUseCase).apply {
            charactersResponse.observeForever(charactersObserver)
            moreCharactersResponse.observeForever(moreCharactersObserver)
            failure.observeForever(isErrorObserver)
        }
    }

    @Test
    fun `should emit get characters list on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            // GIVEN
            val charactersList = Success(mockCharacters(finalOffset).map {
                it.toCharacterListDomain()
            })
            val channel = Channel<Success<List<CharacterList>>>()
            val flow = channel.consumeAsFlow()

            // WHEN
            doReturn(flow)
                .whenever(repository)
                .getCharactersList(initialOffset)

            launch {
                channel.send(charactersList)
                channel.close(IOException())
            }

            viewModel.getCharactersList()

            // THEN
            verify(charactersObserver).onChanged(
                charactersList.data!!.map { it.toCharacterView() }
            )
        }

    @Test
    fun `should emit error on get characters list failure`() =
        coroutinesRule.dispatcher.runBlockingTest {
            // GIVEN
            val charactersList = Success(mockCharacters(finalOffset).map {
                it.toCharacterListDomain()
            })
            val expectedError = Error(Throwable())
            val channel = Channel<Success<List<CharacterList>>>()
            val flow = channel.consumeAsFlow()

            // WHEN
            doReturn(flow)
                .whenever(repository)
                .getCharactersList(initialOffset)

            launch {
                channel.send(charactersList)
                channel.close(expectedError.exception)
            }

            viewModel.getCharactersList()

            // THEN
            verify(charactersObserver).onChanged(
                charactersList.data!!.map { it.toCharacterView() }
            )
            verify(isErrorObserver).onChanged(expectedError.exception)
        }

    @Test
    fun `should emit get characters list scrolled on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            // GIVEN
            val charactersList = Success(mockCharacters(finalOffset).map {
                it.toCharacterListDomain()
            })

            val channel = Channel<Success<List<CharacterList>>>()
            val flow = channel.consumeAsFlow()

            // WHEN
            doReturn(flow)
                .whenever(repository)
                .getCharactersList(finalOffset)

            launch {
                channel.send(charactersList)
                channel.close(IOException())
            }

            viewModel.charactersListScrolled()

            // THEN
            verify(moreCharactersObserver).onChanged(
                charactersList.data!!.map { it.toCharacterView() }
            )
        }
}