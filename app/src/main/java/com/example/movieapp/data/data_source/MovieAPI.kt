package com.example.movieapp.data.data_source

import com.example.movieapp.common.APIConstants.API_KEY
import com.example.movieapp.data.model.detail.MovieDetailResponseModel
import com.example.movieapp.data.model.nowplaying.NowPlayingMovieResponseModel
import com.example.movieapp.data.model.upcoming.UpcomingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @Headers("Accept: application/json", "Authorization: $API_KEY")
    @GET("/3/movie/upcoming?language=en-US")
    suspend fun getMovieWithPageFromAPI(
        @Query("page") page: Int
    ): Response<UpcomingMovieResponse>

    @Headers("Accept: application/json", "Authorization: $API_KEY")
    @GET("/3/movie/now_playing?language=en-US")
    suspend fun getNowPlayingMovieWithPageFromAPI(
        @Query("page") page: Int
    ): Response<NowPlayingMovieResponseModel>

    @Headers("Accept: application/json", "Authorization: $API_KEY")
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetailFromAPI(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailResponseModel>

}