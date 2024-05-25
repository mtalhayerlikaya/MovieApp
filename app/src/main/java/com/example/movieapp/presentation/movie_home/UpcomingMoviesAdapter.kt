package com.example.movieapp.presentation.movie_home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.APIConstants
import com.example.movieapp.data.model.upcoming.Result
import com.example.movieapp.databinding.UpcomingRvListItemBinding
import java.text.SimpleDateFormat

class UpcomingMoviesAdapter(
    val context: Context,
    var upcomingList: MutableList<Result>,
    val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMoviesVH>() {

    inner class UpcomingMoviesVH(private val binding: UpcomingRvListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(upcomingItem: Result) {
            with(binding) {
                homeRecyclerviewTitle.text = upcomingItem.title
                homeRecyclerviewDescription.text = upcomingItem.overview
                SimpleDateFormat("yyyy-MM-dd").parse(upcomingItem.release_date)?.let { date ->
                    dateText.text = SimpleDateFormat("dd.MM.yyyy").format(date)
                }
                Glide.with(context)
                    .load(APIConstants.IMAGE_BASE_URL + upcomingItem.backdrop_path)
                    .placeholder(R.drawable.slider_progress_animation)
                    .centerCrop()
                    .into(homeRecyclerviewImage)
                upcomingRvItemRoot.setOnClickListener {
                    onClick.invoke(upcomingItem.id)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesVH {
        val inflate = UpcomingRvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingMoviesVH(inflate)
    }

    override fun onBindViewHolder(holder: UpcomingMoviesVH, position: Int) {
        holder.bind(upcomingList[position])
    }

    override fun getItemCount(): Int {
        return upcomingList.size
    }

}