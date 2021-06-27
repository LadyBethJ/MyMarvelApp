package com.mjb.characters.core.di.provider

import com.mjb.characters.core.di.component.CharactersComponent

interface CharactersComponentProvider {

    fun getCharactersComponent(): CharactersComponent
}