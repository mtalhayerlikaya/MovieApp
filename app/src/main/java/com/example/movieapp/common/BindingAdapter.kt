package com.example.movieapp.common

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.uimodel.MovieDetailUI
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@BindingAdapter("setImage")
fun setImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let { url ->
        Glide.with(imageView)
            .load(APIConstants.IMAGE_BASE_URL + url)
            .placeholder(R.drawable.slider_progress_animation)
            .centerCrop()
            .into(imageView)
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setDate")
fun setDate(
    tvMovieDate: TextView,
    date: String?
) {
    date?.let { date1 ->
        SimpleDateFormat("yyyy-MM-dd").parse(date1)?.let { date2 ->
            tvMovieDate.text = SimpleDateFormat("dd.MM.yyyy").format(date2)
        }
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setTitle")
fun setTitle(
    tvMovieTitle: TextView,
    detailModel: MovieDetailUI?
) {
    detailModel?.let { model ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val year = LocalDate.parse(model.release_date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).year
            tvMovieTitle.text = "${model.title} ($year)"
        }
    }

}
