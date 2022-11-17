package com.example.exercise403_tabnavigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private val TAB_GENRES_SCROLLABLE = listOf(
    R.string.action,
    R.string.comedy,
    R.string.drama,
    R.string.sci_fi,
    R.string.family,
    R.string.crime,
    R.string.history
)

private val TAB_GENRES_FIXED = listOf(
    R.string.action,
    R.string.comedy,
    R.string.drama
)

//FragmentManager used to manage fragments used in the Activity
class MovieGenresPagerAdapter(private val context: Context, fm: FragmentManager):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    //returns total no. of items to be displayed
    override fun getCount(): Int {
        //return TAB_GENRES_FIXED.size
        return TAB_GENRES_SCROLLABLE.size
    }
    //Gives MoviesFragment the position in the list OR creates MoviesFragment if accessing it
    //for first time by passing in genre title to display in fragment
    override fun getItem(position: Int): Fragment {
        //return MoviesFragment.newInstance(context.resources.getString(TAB_GENRES_FIXED[position]))
        return MoviesFragment.newInstance(context.resources.getString(TAB_GENRES_SCROLLABLE[position]))
    }

    //optionally implemented unlike getCount() and getItem(), require manual override
    //retrieves genre title at the specified position in list using a specific position
    override fun getPageTitle(position: Int): CharSequence? {
        //return context.resources.getString(TAB_GENRES_FIXED[position])
        return context.resources.getString(TAB_GENRES_SCROLLABLE[position])
    }


}