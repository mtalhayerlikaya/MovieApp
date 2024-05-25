package com.example.movieapp.data.repository

import com.example.movieapp.common.Resource
import com.example.movieapp.data.uimodel.MovieDetailUI
import com.example.movieapp.data.uimodel.NowPlayingMovieUI
import com.example.movieapp.data.uimodel.UpcomingMovieUI
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieWithPageFromAPI(page: Int): Flow<Resource<UpcomingMovieUI>>
    suspend fun getNowPlayingMovieWithPageFromAPI(page: Int): Flow<Resource<NowPlayingMovieUI>>
    suspend fun getMovieDetailFromAPI(movieId: Int): Flow<Resource<MovieDetailUI>>
}