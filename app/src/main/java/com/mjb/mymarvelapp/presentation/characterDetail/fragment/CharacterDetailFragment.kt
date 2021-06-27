package com.mjb.mymarvelapp.presentation.characterDetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.mjb.mymarvelapp.R
import com.mjb.mymarvelapp.databinding.FragmentCharacterDetailBinding
//import com.mjb.mymarvelapp.core.di.component.ViewComponent
import com.mjb.mymarvelapp.presentation.base.BaseFragment
import com.mjb.characters.data.model.view.CharacterDetailView
import com.mjb.core.extensions.failure
import com.mjb.core.extensions.observe
import com.mjb.mymarvelapp.presentation.characterDetail.viewmodel.CharacterDetailViewModel
//import javax.inject.Inject
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment() {

    //TODO borrar dagger
    //@Inject
    //lateinit var characterDetailViewModel: CharacterDetailViewModel
    //override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)

    private val characterDetailViewModel by viewModel<CharacterDetailViewModel>()

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(characterDetailViewModel) {
            observe(showSpinner, ::showSpinner)
            failure(failure, ::handleFailure)
            failure(badRequest, ::handleFailure)
            observe(characterDetailResponse, ::setCharacterDetail)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterDetailViewModel.getCharacterDetail(args.characterId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCharacterDetail(characterDetailView: CharacterDetailView?) {
        characterDetailView?.let {
            binding.apply {
                imageProfile.load(
                    characterDetailView.image,
                    ImageLoader.Builder(requireContext()).componentRegistry {
                        add(SvgDecoder(requireContext()))
                    }.build()
                )
                textName.text = characterDetailView.name
                if (characterDetailView.description?.isNotEmpty()!!) {
                    textDescription.text = characterDetailView.description
                } else {
                    textDescription.text = requireContext().resources.getText(R.string.no_description)
                }
            }
        }
    }

    private fun handleFailure(failure: Throwable?) {
        showAlertDialog(failure)
    }
}
