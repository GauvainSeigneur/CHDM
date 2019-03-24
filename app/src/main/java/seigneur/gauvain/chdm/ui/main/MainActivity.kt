package seigneur.gauvain.chdm.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.ui.objects.ObjectFragment
import seigneur.gauvain.chdm.ui.home.HomeFragment
import seigneur.gauvain.chdm.ui.info.InfoFragment
import seigneur.gauvain.chdm.ui.visit.VisitFragment
import seigneur.gauvain.chdm.utils.FragmentStateManager

class MainActivity : AppCompatActivity() {

    private var mFragmentStateManager: FragmentStateManager? = null

    /**
     * From AppCompatActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)
        mBottomAppbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.mipmap.ic_launcher)
        }

        mNavigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()
            // Handle navigation view item clicks here.
            when (menuItem.itemId) {
                R.id.action_example -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show()
                }
            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            true
        }


        initFragmentManager(savedInstanceState)
        mBottomNavigation.setOnNavigationItemSelectedListener { item ->
            showFragment(  getNavPositionFromMenuItem(item))
            true
        }

        mBottomNavigation.setOnNavigationItemReselectedListener { _ ->
        }
    }

    /**
     * toolbar button click
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
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
                    2 -> return VisitFragment()
                    3 -> return InfoFragment()
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
            R.id.navigation_visit               ->  2
            R.id.navigation_info                ->  3
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
