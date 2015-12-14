/*Copyright 2015 Simon Gerst

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package de.intrigus.chem

import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import de.intrigus.chem.fragments.AboutFragment
import de.intrigus.chem.fragments.PseDetailFragment
import de.intrigus.chem.fragments.PseListFragment

class ChemieActivity : AppCompatActivity() {
    lateinit private var drawerLayout: DrawerLayout
    lateinit private var drawerToggle: ActionBarDrawerToggle
    lateinit private var drawerNavigation: NavigationView
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.content)
            when (fragment) {
                is PseDetailFragment -> drawerNavigation.setCheckedItem(R.id.periodic_table)
                is PseListFragment -> drawerNavigation.setCheckedItem(R.id.periodic_table)
                is AboutFragment -> drawerNavigation.setCheckedItem(R.id.about_menu_item)
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.content, PseListFragment ()).commit()

        drawerNavigation = findViewById(R.id.drawer_nav) as NavigationView
        drawerNavigation.setNavigationItemSelectedListener({ item ->

            when (item.itemId) {
                R.id.periodic_table -> switchScreen(PseListFragment())
            //R.id.chemical_balancer -> switchScreen(PseListFragment())
                R.id.about_menu_item -> switchScreen(AboutFragment())
            }
            drawerLayout.closeDrawer(drawerNavigation)
            true

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupToolbar(findViewById(R.id.toolbar) as Toolbar)
        drawerToggle.syncState()
    }

    public fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar.setHomeButtonEnabled(true)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.setDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    fun switchScreen(fragment: Fragment, animated: Boolean = true) {
        if (animated) {
            beginFragment(fragment)
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.content, fragment)
                    .commit()
        } else {
            beginFragment(fragment)
                    .replace(R.id.content, fragment)
                    .commit()
        }

        currentFragment = fragment


    }

    /**
     * @return returns the fragment transaction
     */
    private fun beginFragment(fragment: Fragment): FragmentTransaction {
        // Replace the current fragment with the same fragment, but don't add it to the backstack
        if (fragment == currentFragment) {
            return supportFragmentManager.beginTransaction()
        } else {
            return supportFragmentManager.beginTransaction().addToBackStack(null)
        }
    }

}
