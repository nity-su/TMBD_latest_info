package com.anlyn.presentation.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anlyn.presentation.common.ViewModelFactory
import com.anlyn.user_rating.R
import com.anlyn.user_rating.databinding.HomeFragmentBinding
import com.anlyn.presentation.home.adapter.MovieRelcAdapter
import com.anlyn.presentation.home.adapter.SeriesRelcAdapter
import com.anlyn.presentation.home.listener.OnHomeFragListener
import com.anlyn.presentation.movie.MovieListFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : Fragment(), OnHomeFragListener {


    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var viewModelFactory:ViewModelProvider.Factory

    private lateinit var binding: HomeFragmentBinding

    companion object {
        fun newInstance() = HomeFragment()
        fun tag():String? = this::class.simpleName
    }

//    private lateinit var viewModel: HomeViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
//        binding = HomeFragmentBinding.inflate(layoutInflater)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.home_fragment, container, false)
        binding = HomeFragmentBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        initHomeMovieRelc()
        initSeriesRelc()
    }
    fun initHomeMovieRelc(){
        val recyclerView = binding.homeMovieRelc
        val adapter = MovieRelcAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = adapter

        viewModel.movieState.observe(viewLifecycleOwner, Observer {
            if(!it.isLoading)
                adapter.setList(it.movieList!!)
        })
    }

    fun initSeriesRelc(){
        val adapter = SeriesRelcAdapter()

        binding.homeSeriesRelc.also { relc ->
            relc.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            relc.adapter = adapter
        }

        viewModel.seriesState.observe(viewLifecycleOwner,{
            if(!it.isLoading && it.list!=null)
               adapter.setList(it.list)
        })
    }

    override fun changeMovieListFrag() {
        val ft = parentFragmentManager.beginTransaction()
        ft.add(R.id.mActivityConr,MovieListFragment.newInstance(),MovieListFragment.tag())
        ft.addToBackStack(MovieListFragment.tag())

        ft.hide(this)
        ft.show(MovieListFragment.newInstance())

        ft.commit()
    }
}