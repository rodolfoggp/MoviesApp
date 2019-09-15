package com.rodolfogusson.testepag.view.tabbed.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.view.favorites.FavoritesFragment
import com.rodolfogusson.testepag.view.movieslist.MoviesListFragment

class TabbedPagerAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesListFragment()
            else -> FavoritesFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.movies)
            else -> context.getString(R.string.favorites)
        }
    }
}