package com.example.movieapp.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.Resource
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.uimodel.MovieDetailUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movieDetail =
        MutableStateFlow<Resource<MovieDetailUI>>(Resource.Empty)

    val movieDetail: StateFlow<Resource<MovieDetailUI>> =
        _movieDetail

    fun getMovieDetailFromAPI(movieId: Int) = viewModelScope.launch {
        movieRepository.getMovieDetailFromAPI(movieId).collect(collector = { movieDetail ->
            _movieDetail.value = movieDetail
        })
    }
}