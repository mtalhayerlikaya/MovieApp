package com.example.movieapp.presentation.movie_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.Resource
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.uimodel.NowPlayingMovieUI
import com.example.movieapp.data.uimodel.UpcomingMovieUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _upcomingMovies =
        MutableSharedFlow<Resource<UpcomingMovieUI>>(replay = 0)

    val upcomingMovies: SharedFlow<Resource<UpcomingMovieUI>> =
        _upcomingMovies

    private val _nowPlayingMovies =
        MutableSharedFlow<Resource<NowPlayingMovieUI>>(replay = 0)

    val nowPlayingMovies: SharedFlow<Resource<NowPlayingMovieUI>> =
        _nowPlayingMovies

    fun getMovieWithPageFromAPI(page: Int) = viewModelScope.launch {
        movieRepository.getMovieWithPageFromAPI(page).collect(collector = { upcomingMovie ->
            _upcomingMovies.emit(upcomingMovie)
        })
    }
    fun getNowPlayingMovieWithPageFromAPI(page: Int) = viewModelScope.launch {
        movieRepository.getNowPlayingMovieWithPageFromAPI(page).collect(collector = { nowPlayingMovie ->
            _nowPlayingMovies.emit(nowPlayingMovie)
        })
    }

}