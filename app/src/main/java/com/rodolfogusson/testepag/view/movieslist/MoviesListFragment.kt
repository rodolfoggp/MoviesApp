package com.rodolfogusson.testepag.view.movieslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModel
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModelFactory
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
        viewModel = ViewModelProviders.of(this, MoviesListViewModelFactory())
            .get(MoviesListViewModel::class.java)
        setupLayout()
    }

    override fun onResume() {
        registerObservers()
        super.onResume()
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
            if (!it.hasError) {
                it?.data?.let { data ->
                    MoviesListAdapter(data).apply {
                        recyclerView.adapter = this
                        notifyDataSetChanged()
                    }
                }
            } else {
                context?.let { context ->
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.dialog_error))
                        .setMessage(getString(R.string.fetch_movies_error))
                        .setPositiveButton(getString(R.string.dialog_ok)) { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        })
    }
}
