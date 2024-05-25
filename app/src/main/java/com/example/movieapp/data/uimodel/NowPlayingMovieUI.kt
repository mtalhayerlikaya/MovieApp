package com.example.movieapp.data.uimodel

import com.example.movieapp.data.model.nowplaying.Dates
import com.example.movieapp.data.model.nowplaying.Result

data class NowPlayingMovieUI(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
