package com.rodolfogusson.testepag.view.tabbed

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rodolfogusson.testepag.R
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TabbedActivityTest {

    private lateinit var activity: TabbedActivity

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(TabbedActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    @Throws(Exception::class)
    fun `activity should not be null`() {
        assertNotNull(activity)
    }

    @Test
    fun `activity should have a TabLayout, inside ViewPager, with two tabs`() {
        val pager = activity.findViewById<ViewPager>(R.id.viewpager)
        val tabs = pager.findViewById<TabLayout>(R.id.tabs)
        assert(tabs.tabCount == 2)
    }

    @Test
    fun `first tab on TabLayout should be named as the string movies`() {
        val tabs = activity.findViewById<TabLayout>(R.id.tabs)
        val tab = tabs.getTabAt(0)
        val name = tab!!.text
        assert(name == activity.getString(R.string.movies))
    }

    @Test
    fun `second tab on TabLayout should be named as the string favorites`() {
        val tabs = activity.findViewById<TabLayout>(R.id.tabs)
        val tab = tabs.getTabAt(1)
        val name = tab!!.text
        assert(name == activity.getString(R.string.favorites))
    }

    @Test
    fun `action bar elevation should be zero`() {
        assert(activity.supportActionBar!!.elevation == 0.0f)
    }
}