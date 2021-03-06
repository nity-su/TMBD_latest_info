package com.anlyn.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anlyn.domain.usecase.GetMovieUseCase
import com.anlyn.domain.usecase.GetSeriesUseCase
import com.anlyn.presentation.common.ViewModelFactory
import com.anlyn.presentation.home.HomeViewModel
import com.anlyn.presentation.movie.MovieListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class ViewModelModule{
    @Provides
    fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>,@JvmSuppressWildcards Provider<ViewModel>>):ViewModelProvider.Factory{
        return ViewModelFactory(providerMap)
    }
    //Dagger에서 ViewModelFactory로 확실하게 적어줄것
    @Provides
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun provideHomeViewModel(getMovieUseCase: GetMovieUseCase,getSeriesUseCase: GetSeriesUseCase):ViewModel{
        return HomeViewModel(getMovieUseCase,getSeriesUseCase)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    fun provideMovieListVM(getMovieUseCase: GetMovieUseCase):ViewModel{
        return MovieListViewModel(getMovieUseCase)
    }

}