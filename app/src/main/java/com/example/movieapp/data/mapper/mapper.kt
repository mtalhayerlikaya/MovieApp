package com.example.movieapp.data.mapper

import com.example.movieapp.data.model.detail.MovieDetailResponseModel
import com.example.movieapp.data.model.nowplaying.NowPlayingMovieResponseModel
import com.example.movieapp.data.model.upcoming.UpcomingMovieResponse
import com.example.movieapp.data.uimodel.MovieDetailUI
import com.example.movieapp.data.uimodel.NowPlayingMovieUI
import com.example.movieapp.data.uimodel.UpcomingMovieUI

fun UpcomingMovieResponse.toUpcomingMovieUI() = UpcomingMovieUI(
    dates = dates,
    page = page,
    results = results,
    total_pages = total_pages,
    total_results = total_results,
)

fun NowPlayingMovieResponseModel.toNowPlayingMovieUI() = NowPlayingMovieUI(
    dates = dates,
    page = page,
    results = results,
    total_pages = total_pages,
    total_results = total_results,
)

fun MovieDetailResponseModel.toMovieDetailUI() = MovieDetailUI(
    adult = adult,
    backdrop_path = backdrop_path,
    budget = budget,
    genres = genres,
    homepage = homepage,
    id = id,
    imdb_id = imdb_id,
    original_language = original_language,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    poster_path = poster_path,
    production_companies = production_companies,
    production_countries = production_countries,
    release_date = release_date,
    revenue = revenue,
    runtime = runtime,
    spoken_languages = spoken_languages,
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    vote_average = vote_average,
    vote_count = vote_count
)