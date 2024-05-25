package com.example.movieapp.presentation.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.common.Resource
import com.example.movieapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var clickedID by Delegates.notNull<Int>()
    private val detailViewModel: DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: DetailFragmentArgs by navArgs()
        clickedID = bundle.clickedItemID
        detailViewModel.getMovieDetailFromAPI(clickedID)
        collectData()
        onBackPressed()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(binding.imageView3).popBackStack()
            }
        })
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                detailViewModel.movieDetail.collect { state ->
                    when (state) {
                        is Resource.Success -> {
                            progressBarDetail.visibility = View.GONE
                            state.data?.let { movieDetail ->
                                detailModel = movieDetail
                            }
                        }
                        is Resource.Loading -> {
                            progressBarDetail.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            progressBarDetail.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}