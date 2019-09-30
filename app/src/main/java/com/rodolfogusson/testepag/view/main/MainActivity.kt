package com.rodolfogusson.testepag.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.view.main.adapter.ViewPagerAdapter
import com.rodolfogusson.testepag.view.main.favorites.FavoritesFragment
import com.rodolfogusson.testepag.view.main.movies.MoviesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setupNavigation()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.elevation = 0.0f
    }

    private fun setupNavigation() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MoviesFragment())
        adapter.addFragment(FavoritesFragment())

        viewpager.adapter = adapter

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.moviesListFragment -> viewpager.currentItem = 0
                R.id.favoritesFragment -> viewpager.currentItem = 1
            }
            false
        }
    }
}
