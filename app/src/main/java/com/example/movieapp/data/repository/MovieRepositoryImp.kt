package com.example.movieapp.data.repository

import com.example.movieapp.common.Resource
import com.example.movieapp.data.data_source.RemoteDataSource
import com.example.movieapp.data.mapper.toMovieDetailUI
import com.example.movieapp.data.mapper.toNowPlayingMovieUI
import com.example.movieapp.data.mapper.toUpcomingMovieUI
import com.example.movieapp.data.uimodel.MovieDetailUI
import com.example.movieapp.data.uimodel.NowPlayingMovieUI
import com.example.movieapp.data.uimodel.UpcomingMovieUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val remoteDataSource: RemoteDataSource) : MovieRepository {

    override suspend fun getMovieWithPageFromAPI(page: Int): Flow<Resource<UpcomingMovieUI>> =
        flow {
            val response = remoteDataSource.getMovieWithPageFromRemote(page)
            emit(Resource.Loading)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    emit(Resource.Success(response.body()!!.toUpcomingMovieUI()))
                } else {
                    emit(Resource.Error("Response is null"))
                }
            } else {
                emit(Resource.Error(response.message()))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getNowPlayingMovieWithPageFromAPI(page: Int): Flow<Resource<NowPlayingMovieUI>> =
        flow {
            val response = remoteDataSource.getNowPlayingMovieWithPageFromRemote(page)
            emit(Resource.Loading)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    emit(Resource.Success(response.body()!!.toNowPlayingMovieUI()))
                } else {
                    emit(Resource.Error("Response is null"))
                }
            } else {
                emit(Resource.Error(response.message()))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetailFromAPI(movieId: Int): Flow<Resource<MovieDetailUI>> =
        flow {
            val response = remoteDataSource.getMovieDetailFromRemote(movieId)
            emit(Resource.Loading)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    emit(Resource.Success(response.body()!!.toMovieDetailUI()))
                } else {
                    emit(Resource.Error("Response is null"))
                }
            } else {
                emit(Resource.Error(response.message()))
            }
        }.flowOn(Dispatchers.IO)


}