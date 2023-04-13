package com.rzl.app_github_user.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rzl.app_github_user.ui.follow.FollowFragment

class ViewPagerAdapter (activity : AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = FollowFragment()
        var tabTitle: String

        tabTitle = when (position){
            0-> FollowFragment.GIT_FOLLOWERS
            1-> FollowFragment.GIT_FOLLOWING
            else -> ""
        }
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.TAB_TITLES, tabTitle)
        }
        return fragment
    }
}