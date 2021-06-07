package com.mjb.mymarvelapp.presentation.characterDetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.mjb.mymarvelapp.R
import com.mjb.mymarvelapp.databinding.FragmentCharacterDetailBinding
import com.mjb.mymarvelapp.infrastructure.di.component.ViewComponent
import com.mjb.mymarvelapp.presentation.base.BaseFragment
import com.mjb.mymarvelapp.presentation.characterDetail.models.CharacterDetailView
import com.mjb.mymarvelapp.presentation.characterDetail.viewmodel.CharacterDetailViewModel
import com.mjb.mymarvelapp.presentation.utils.extensions.failure
import com.mjb.mymarvelapp.presentation.utils.extensions.observe
import com.mjb.mymarvelapp.presentation.utils.extensions.showInfoAlertDialog
import javax.inject.Inject

class CharacterDetailFragment : BaseFragment() {

    @Inject
    lateinit var characterDetailViewModel: CharacterDetailViewModel
    override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)

    private lateinit var binding: FragmentCharacterDetailBinding
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(characterDetailViewModel) {
            observe(showSpinner, ::showSpinner)
            failure(failure, ::handleFailure)
            failure(badRequest, ::handleBadRequest)
            observe(characterDetailResponse, ::setCharacterDetail)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterDetailViewModel.getCharacterDetail(args.characterId)
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
                if (characterDetailView.description.isNotEmpty()) {
                    textDescription.text = characterDetailView.description
                } else {
                    textDescription.text = requireContext().resources.getText(R.string.no_description)
                }
            }
        }
    }

    private fun handleFailure(failure: Throwable?) {
        showInfoAlertDialog {
            setTitle(getString(R.string.error_title))
            setText(failure?.message ?: getString(R.string.common_error))
            btnAccept {
                findNavController().navigateUp()
            }
        }.show()
    }

    private fun handleBadRequest(failure: Throwable?) {
        showInfoAlertDialog {
            setTitle(getString(R.string.bad_request))
            setText(failure?.message ?: getString(R.string.common_error))
            btnAccept {
                findNavController().navigateUp()
            }
        }.show()
    }
}
