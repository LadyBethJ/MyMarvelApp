package com.mjb.mymarvelapp.presentation.charactersList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.mjb.mymarvelapp.R
import com.mjb.mymarvelapp.databinding.ListItemCharacterBinding
import com.mjb.mymarvelapp.presentation.charactersList.fragment.CharactersListFragmentDirections
import com.mjb.mymarvelapp.presentation.charactersList.models.CharacterListView

class CharactersListAdapter :
    ListAdapter<CharacterListView, CharactersListAdapter.CharactersViewHolder>(DIFF_CALLBACK) {

    private lateinit var binding: ListItemCharacterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        binding =
            ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        Companion.navOptions = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CharactersListAdapter.CharactersViewHolder,
        position: Int
    ) {
        val characterItem = getItem(position)
        if (characterItem != null) {
            holder.bind(characterItem)
        }
    }

    inner class CharactersViewHolder(private val itemBinding: ListItemCharacterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(character: CharacterListView) {
            itemBinding.apply {
                textId.text =
                    root.context.getString(R.string.character_id, character.id.toString())
                textName.text = character.name
                imageProfile.load(
                    character.image,
                    ImageLoader.Builder(itemBinding.root.context).componentRegistry {
                        add(SvgDecoder(itemBinding.root.context))
                    }.build()
                )

                val action = CharactersListFragmentDirections.actionListToCharacterDetail(
                    character.id!!
                )

                root.setOnClickListener {
                    root.findNavController()
                        .navigate(action, Companion.navOptions)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<CharacterListView>() {

            override fun areItemsTheSame(
                oldCharacter: CharacterListView,
                newCharacter: CharacterListView
            ) = oldCharacter.id == newCharacter.id

            override fun areContentsTheSame(
                oldCharacter: CharacterListView,
                newCharacter: CharacterListView
            ) = oldCharacter == newCharacter
        }
        private lateinit var navOptions: NavOptions
    }
}
