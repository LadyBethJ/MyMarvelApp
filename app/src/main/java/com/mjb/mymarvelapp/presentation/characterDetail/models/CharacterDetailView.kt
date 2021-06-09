package com.mjb.mymarvelapp.presentation.characterDetail.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterDetailView(
    val name: String?,
    val image: String?,
    val description: String?
) : Parcelable
