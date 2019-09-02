package com.rodolfogusson.testepag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class TabbedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
/*        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.addOnTabSelectedListener(TabLayout.OnTabSelectedListener())*/
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.elevation = 0.0f
    }
}
