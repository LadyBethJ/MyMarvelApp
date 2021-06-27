package com.mjb.characters.data.model.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterDetailView(
    val name: String?,
    val image: String?,
    val description: String?
) : Parcelable
