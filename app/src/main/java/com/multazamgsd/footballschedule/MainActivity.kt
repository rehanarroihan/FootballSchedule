package com.multazamgsd.footballschedule

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.multazamgsd.footballschedule.fragment.FavoriteFragment
import com.multazamgsd.footballschedule.fragment.NextFragment
import com.multazamgsd.footballschedule.fragment.PreviousFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            var fragment: Fragment
            fragment = PreviousFragment()
            if (position == 0) {
                fragment = PreviousFragment()
            } else if (position == 1) {
                fragment = NextFragment()
            } else if (position == 2) {
                fragment = FavoriteFragment()
            }
            return fragment
        }

        override fun getCount(): Int {
            return 3
        }
    }
}
