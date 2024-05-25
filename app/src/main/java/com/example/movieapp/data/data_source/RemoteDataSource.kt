package com.example.movieapp.data.data_source

import com.example.movieapp.data.model.detail.MovieDetailResponseModel
import com.example.movieapp.data.model.nowplaying.NowPlayingMovieResponseModel
import com.example.movieapp.data.model.upcoming.UpcomingMovieResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getMovieWithPageFromRemote(page: Int): Response<UpcomingMovieResponse>
    suspend fun getNowPlayingMovieWithPageFromRemote(page: Int): Response<NowPlayingMovieResponseModel>
    suspend fun getMovieDetailFromRemote(movieId: Int): Response<MovieDetailResponseModel>
}