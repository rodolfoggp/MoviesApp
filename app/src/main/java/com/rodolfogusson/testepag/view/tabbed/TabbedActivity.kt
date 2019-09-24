package com.rodolfogusson.testepag.view.tabbed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.view.favorites.FavoritesFragment
import com.rodolfogusson.testepag.view.movieslist.MoviesListFragment
import com.rodolfogusson.testepag.view.tabbed.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class TabbedActivity : AppCompatActivity() {

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
        adapter.addFragment(MoviesListFragment())
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
