package com.rodolfogusson.testepag.view.movieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.*

class MoviesListFragment : Fragment() {

    private lateinit var viewModel: MoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesListViewModel::class.java)
        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
    }

    private fun registerObservers() {
        recyclerView.adapter = MoviesListAdapter(listOf("a", "b", "c"))
        /*viewModel.movies.observe(this, Observer {
            it?.let {

            }
        })*/
    }
}
