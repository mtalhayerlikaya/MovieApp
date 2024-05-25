package com.example.movieapp.presentation.movie_home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.APIConstants.IMAGE_BASE_URL
import com.example.movieapp.data.model.nowplaying.Result
import com.example.movieapp.databinding.ImageSliderLayoutItemBinding
import com.smarteist.autoimageslider.SliderViewAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class SliderAdapter(
    var sliderList: MutableList<Result>,
    private val onClickListener: (Int) -> Unit
) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    class SliderAdapterVH(var binding: ImageSliderLayoutItemBinding) : ViewHolder(binding.root) {
        fun bind(sliderItem: Result) {
            with(binding){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val year = LocalDate.parse(sliderItem.release_date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).year
                    tvSliderTitle.text = "${sliderItem.title} ($year)"
                }
                sliderDescription.text = sliderItem.overview
                Glide.with(root.context)
                    .load(IMAGE_BASE_URL + sliderItem.backdrop_path)
                    .placeholder(R.drawable.slider_progress_animation)
                    .into(ivAutoImageSlider)
            }
        }
    }

    override fun getCount(): Int {
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = ImageSliderLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = sliderList[position]
        viewHolder.bind(sliderItem)
        viewHolder.binding.sliderRootLy.setOnClickListener {
            onClickListener.invoke(sliderItem.id)
        }
    }
}

