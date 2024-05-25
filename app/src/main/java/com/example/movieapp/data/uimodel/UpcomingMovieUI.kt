package com.example.movieapp.data.uimodel

import com.example.movieapp.data.model.upcoming.Dates
import com.example.movieapp.data.model.upcoming.Result

data class UpcomingMovieUI(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
