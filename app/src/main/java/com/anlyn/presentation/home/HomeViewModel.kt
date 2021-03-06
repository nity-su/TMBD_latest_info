package com.anlyn.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anlyn.domain.usecase.GetMovieUseCase
import com.anlyn.domain.usecase.GetSeriesUseCase
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getMovieUseCase: GetMovieUseCase, private val getSeriesUseCase: GetSeriesUseCase) : ViewModel() {
// @Inject constructor()
      private var _movieState=MutableLiveData<MovieViewState>()
      var movieState:LiveData<MovieViewState> = _movieState

    private var _seriesState=MutableLiveData<SeriesViewState>()
    var seriesState:LiveData<SeriesViewState> = _seriesState
           init {
               _movieState.value = MovieViewState(isLoading = true)
               _seriesState.value = SeriesViewState(isLoading = true)
               getMovie()
               getSeries()
           }

        fun getMovie(){
            getMovieUseCase.execute("ko",1).subscribe({
                //예외처리
                _movieState.value = movieState.value!!.copy(false,it)
                Log.d(this::class.simpleName,"success")
            },{
                if(it is SocketTimeoutException){
                    Log.d(this::class.simpleName,"TimeOut")
                }else if (it is IOException){
                    Log.d(this::class.simpleName,"IOException")
                }
                Log.d(this::class.simpleName,it.message.toString())
            },{

            })
        }
        fun getSeries(){
            getSeriesUseCase.execute("ko",1).subscribe({
                _seriesState.value = seriesState.value!!.copy(isLoading = false,it)
                Log.d(this::class.simpleName,"success")
            },{
                Log.e(this::class.simpleName,it.message.toString())
            })
        }

}