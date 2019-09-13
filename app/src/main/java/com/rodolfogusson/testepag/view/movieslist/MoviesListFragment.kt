package com.rodolfogusson.testepag.view.movieslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModel
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies_list.*


class MoviesListFragment : Fragment() {

    private lateinit var viewModel: MoviesListViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private val lastVisibleItemPosition: Int
        get() = layoutManager.findLastVisibleItemPosition()

    private val adapter = MoviesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, MoviesListViewModelFactory())
            .get(MoviesListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        progress.visibility = View.VISIBLE
        layoutManager = LinearLayoutManager(this.activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this.activity,
                DividerItemDecoration.VERTICAL
            )
        )
        setRecyclerViewScrollListener()
        recyclerView.adapter = adapter
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    //Toast.makeText(activity, "Final", Toast.LENGTH_SHORT).show()
                    viewModel.loadMoreMovies()
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun registerObservers() {
        observeMovies()
        observeLoadingState()
    }

    private fun observeMovies() {
        viewModel.movies.observe(this, Observer {
            if (!it.hasError) {
                it?.data?.let { data ->
                    viewModel.isLoading.value = false

                    adapter.apply {
                        this.data = data
                    }
                }
            } else {
                showErrorDialog()
            }
        })
    }

    private fun showErrorDialog() {
        context?.let { context ->
            AlertDialog.Builder(context)
                .setTitle(getString(R.string.dialog_error))
                .setMessage(getString(R.string.fetch_movies_error))
                .setPositiveButton(getString(R.string.dialog_ok)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun observeLoadingState() {
        viewModel.isLoading.observe(this, Observer { isLoading ->
            progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}
