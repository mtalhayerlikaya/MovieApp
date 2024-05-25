package com.example.movieapp.data.data_source

import com.example.movieapp.data.model.detail.MovieDetailResponseModel
import com.example.movieapp.data.model.nowplaying.NowPlayingMovieResponseModel
import com.example.movieapp.data.model.upcoming.UpcomingMovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(private val movieAPI: MovieAPI) : RemoteDataSource {
    override suspend fun getMovieWithPageFromRemote(page: Int): Response<UpcomingMovieResponse> =
        movieAPI.getMovieWithPageFromAPI(page)

    override suspend fun getNowPlayingMovieWithPageFromRemote(page: Int): Response<NowPlayingMovieResponseModel> =
        movieAPI.getNowPlayingMovieWithPageFromAPI(page)

    override suspend fun getMovieDetailFromRemote(movieId: Int): Response<MovieDetailResponseModel> =
        movieAPI.getMovieDetailFromAPI(movieId)
}