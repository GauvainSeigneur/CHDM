package seigneur.gauvain.chdm.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.ui.objects.ObjectFragment
import seigneur.gauvain.chdm.ui.home.HomeFragment
import seigneur.gauvain.chdm.utils.FragmentStateManager

class MainActivity : AppCompatActivity() {


    private var mFragmentStateManager: FragmentStateManager? = null

    /**
     * From AppCompatActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initFragmentManager(savedInstanceState)
        mBottomNavigation.setOnNavigationItemSelectedListener { item ->
            showFragment(  getNavPositionFromMenuItem(item))
            true
        }

        mBottomNavigation.setOnNavigationItemReselectedListener { item ->

        }
    }

    /**
     * Initialize Fragment manager and default value
     */
    private fun initFragmentManager(savedInstanceState: Bundle?) {
        mFragmentStateManager = object : FragmentStateManager(mfragmentContainer, supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                when (position) {
                    0 -> return HomeFragment()
                    1 -> return ObjectFragment()
                    3 -> return HomeFragment()
                }
                return HomeFragment()
            }
        }
        if (savedInstanceState == null) {
            mFragmentStateManager?.changeFragment(0)
        }
    }

    /**
     * get position form item id in bottom navigation menu
     */
    private fun getNavPositionFromMenuItem(menuItem: MenuItem): Int {
        return when (menuItem.itemId) {
            R.id.navigation_home                ->  0
            R.id.navigation_dashboard           ->  1
            R.id.navigation_notifications       ->  2
            else                                -> -1
        }
    }

    /**
     * Show a fragment thanks to mFragmentStateManager
     */
    private fun showFragment(pos: Int) {
        mFragmentStateManager?.changeFragment(pos)
    }
}
