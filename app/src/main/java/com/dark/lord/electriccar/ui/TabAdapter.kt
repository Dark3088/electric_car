package com.dark.lord.electriccar.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CarFragment()
            1 -> FavoriteFragment()
            else -> CarFragment()
        }
    }
    override fun getItemCount(): Int = 2
}