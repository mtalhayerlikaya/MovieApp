package com.example.movieapp.presentation.movie_home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.movieapp.R
import com.example.movieapp.common.Resource
import com.example.movieapp.data.model.upcoming.Result
import com.example.movieapp.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: UpcomingMoviesAdapter
    private var isLoading = true
    private var mIsExpanded = true
    private var lastOffset = 0
    private lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getNowPlayingMovieWithPageFromAPI(1)
        homeViewModel.getMovieWithPageFromAPI(1)

        initView()
        collectData()
    }

    private fun initView() {
        with(binding) {
            adapter = UpcomingMoviesAdapter(requireContext(), mutableListOf()) { clickedItemID ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(clickedItemID)
                binding.root.findNavController().navigate(action)
            }
            upcomingRv.adapter = adapter
            upcomingRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            appBarLayoutSlider.addOnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->
                onOffsetChanged(
                    appBarLayout,
                    verticalOffset
                )
            }
            swipeRefreshLayout.setOnRefreshListener {
                clearAdapters()
                homeViewModel.getNowPlayingMovieWithPageFromAPI(1)
                homeViewModel.getMovieWithPageFromAPI(1)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun clearAdapters() {
        adapter.upcomingList.clear()
        sliderAdapter.sliderList.clear()
        adapter.notifyDataSetChanged()
        sliderAdapter.notifyDataSetChanged()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    homeViewModel.upcomingMovies.collect { state ->
                        when (state) {
                            is Resource.Success -> {
                                progressBarHome.visibility = View.GONE
                                state.data?.results?.let { upcomingList ->
                                    addDataToRecyclerView(upcomingList.toMutableList())
                                    setUpcomingRVScrollListener(state.data.page, state.data.total_pages)
                                }
                            }
                            is Resource.Loading -> {
                                progressBarHome.visibility = View.VISIBLE
                            }
                            is Resource.Error -> {
                                progressBarHome.visibility = View.GONE
                                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.nowPlayingMovies.collect { state ->
                    when (state) {
                        is Resource.Success -> {
                            state.data?.results?.let { resultList ->
                                sliderAdapter = SliderAdapter(
                                    resultList.toMutableList()
                                ) { clickedItemID ->
                                    val action =
                                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(clickedItemID)
                                    binding.root.findNavController().navigate(action)
                                }
                                binding.imageSlider.setSliderAdapter(sliderAdapter)
                            }

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {

                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setUpcomingRVScrollListener(page: Int, totalPage: Int) {
        with(binding) {
            upcomingRv.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == adapter.itemCount - 1) {
                        if (page < totalPage) {
                            if (!isLoading) {
                                isLoading = true
                                homeViewModel.getMovieWithPageFromAPI(page + 1)
                            }
                        }
                    }
                    try {
                        val firstPos: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                        if (firstPos > 0) {
                            swipeRefreshLayout.isEnabled = false
                        } else {
                            swipeRefreshLayout.isEnabled = true
                            if (recyclerView.scrollState == 1) if (swipeRefreshLayout.isRefreshing) recyclerView.stopScroll()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        }
    }

    private fun addDataToRecyclerView(list: MutableList<Result>) {
        adapter.upcomingList.addAll(list)
        adapter.notifyDataSetChanged()
        isLoading = false
    }

    private fun setStatusBarColor(color: Int) {
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(color)
    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(R.color.white)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        //if collapsed else expanded
        val collapsed = abs(verticalOffset) - appBarLayout.totalScrollRange == 0
        if (collapsed) {
            binding.swipeRefreshLayout.isEnabled = false
            mIsExpanded = false
            lastOffset = verticalOffset
        } else {
            binding.swipeRefreshLayout.isEnabled = true
            if (!mIsExpanded && abs(verticalOffset) - appBarLayout.totalScrollRange < -88) {

                mIsExpanded = true
                lastOffset = verticalOffset
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}