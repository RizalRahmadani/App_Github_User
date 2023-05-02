package com.rzl.app_github_user.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rzl.app_github_user.ui.follow.FollowersFragment
import com.rzl.app_github_user.ui.follow.FollowFragment

class ViewPagerAdapter (activity : AppCompatActivity, private val login : Bundle) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position){
            0-> fragment = FollowersFragment()
            1-> fragment = FollowFragment()
        }

        fragment?.arguments = login
        return fragment as Fragment
    }
}