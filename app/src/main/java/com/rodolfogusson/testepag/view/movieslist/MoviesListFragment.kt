package com.rodolfogusson.testepag.view.movieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModel
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies_list.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.rodolfogusson.testepag.R


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
        viewModel = ViewModelProviders.of(this, MoviesListViewModelFactory())
            .get(MoviesListViewModel::class.java)
        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this.activity,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun registerObservers() {
        viewModel.movies.observe(this, Observer {
            it?.let {
                MoviesListAdapter(it).apply {
                    recyclerView.adapter = this
                    notifyDataSetChanged()
                }
            }
        })
    }
}
