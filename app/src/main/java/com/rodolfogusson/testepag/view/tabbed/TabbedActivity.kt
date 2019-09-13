package com.rodolfogusson.testepag.view.tabbed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.view.tabbed.adapter.TabbedPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class TabbedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setupTabs()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.elevation = 0.0f
    }

    private fun setupTabs() {
        viewpager.adapter =
            TabbedPagerAdapter(
                supportFragmentManager,
                this
            )
        tabs.setupWithViewPager(viewpager)
        tabs.getTabAt(0)?.icon = ContextCompat.getDrawable(this,
            R.drawable.list
        )
        tabs.getTabAt(1)?.icon = ContextCompat.getDrawable(this,
            R.drawable.favorite
        )
    }
}
