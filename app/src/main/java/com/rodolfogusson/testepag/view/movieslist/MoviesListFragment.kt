package com.rodolfogusson.testepag.view.movieslist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.infrastructure.data.Status
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.view.moviesdetails.MovieDetailsActivity
import com.rodolfogusson.testepag.view.movieslist.adapter.MoviesListAdapter
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModel
import com.rodolfogusson.testepag.viewmodel.movieslist.MoviesListViewModelFactory
import com.rodolfogusson.testepag.viewmodel.movieslist.textFor
import kotlinx.android.synthetic.main.fragment_movies_list.*

class MoviesListFragment : Fragment() {

    private lateinit var viewModel: MoviesListViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = layoutManager.findLastVisibleItemPosition()
    private val adapter = MoviesListAdapter()
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var dialog: AlertDialog

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
        setupSortingSelector()
        setupRecyclerView()
        setupErrorDialog()
    }

    private fun setupSortingSelector() {
        val context: Context = this.context ?: return
        val values = viewModel.orderArray.map { order -> textFor(order, context) }
        val arrayAdapter =
            ArrayAdapter(context, R.layout.sorting_selector_item, values)
        sortingSelector.adapter = arrayAdapter
        sortingSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //print("NOTHING")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("POSITION: ", "$position")
                viewModel.onSortingSelected(position)
            }
        }
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(this.activity)
        recyclerView.layoutManager = layoutManager
        setRecyclerViewScrollListener()
        adapter.clickListener = ::onMovieClicked
        recyclerView.adapter = adapter
    }

    private fun setupErrorDialog() {
        dialog = AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog)
            .setTitle(getString(R.string.dialog_error))
            .setMessage(getString(R.string.fetch_movies_error))
            .setPositiveButton(getString(R.string.dialog_retry)) { dialog, _ ->
                viewModel.retryGetMovies()
                dialog.dismiss()
            }
            .create()
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
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
        viewModel.movies.observe(this, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { movies ->
                        adapter.apply {
                            this.data = movies
                        }
                    }
                }
                Status.ERROR -> {
                    if (!dialog.isShowing) {
                        dialog.show()
                    }
                }
            }
            viewModel.onMoviesLoaded(resource.status)
        })
    }

    private fun observeLoadingState() {
        viewModel.isProgressVisible.observe(this, Observer { visible ->
            progress.visibility = if (visible) View.VISIBLE else View.GONE
        })
        viewModel.isNextPageProgressVisible.observe(this, Observer { visible ->
            if (visible) {
                nextPageProgress.visibility = View.VISIBLE

                // Used to give a better loading effect on the last item
                viewModel.movies.value?.data?.size?.let { size ->
                    recyclerView.scrollToPosition(size - 1)
                }
            } else {
                nextPageProgress.visibility = View.GONE
            }
        })
    }

    private fun onMovieClicked(movie: Movie) {
        print("NAME: ${movie.title}")
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.movieIdKey, movie.id)
        startActivity(intent)
    }
}

