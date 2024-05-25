package com.example.movieapp.di

import com.example.movieapp.data.data_source.RemoteDataSource
import com.example.movieapp.data.data_source.RemoteDataSourceImp
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource
    ): MovieRepository = MovieRepositoryImp(remoteDataSource)

    @Provides
    @Singleton
    fun provideRemoteDataSourceImp(
        remoteDataSource: RemoteDataSourceImp
    ): RemoteDataSource = remoteDataSource


}