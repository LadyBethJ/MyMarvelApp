package com.mjb.mymarvelapp.core.di.component

import com.mjb.mymarvelapp.core.di.module.ViewModule
import com.mjb.mymarvelapp.core.di.scope.ViewScope
import com.mjb.mymarvelapp.presentation.MainActivity
import com.mjb.mymarvelapp.presentation.characterDetail.fragment.CharacterDetailFragment
import com.mjb.mymarvelapp.presentation.charactersList.fragment.CharactersListFragment
import dagger.Subcomponent

@ViewScope
@Subcomponent(
    modules = [
        ViewModule::class
    ]
)
interface ViewComponent {

    fun inject(activity: MainActivity)

    fun inject(charactersListFragment: CharactersListFragment)

    fun inject(characterDetailFragment: CharacterDetailFragment)
}