package com.rzl.app_github_user.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.R
import com.rzl.app_github_user.databinding.ActivityDetailUserBinding
import com.rzl.app_github_user.ui.follow.FollowFragment.Companion.TAB_TITLES
import com.rzl.app_github_user.ui.main.MainActivity



class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val user = intent.getParcelableExtra<ItemsItem>(MainActivity.EXTRA_DATA) as ItemsItem

        viewModel.getDetailUser(user.login)

        viewModel.listUser.observe(this){
                detailUser ->

            Glide.with(this)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgDetailPhoto)
            binding.apply {
                tvDetailName.text = detailUser.name
                tvDetailLogin.text = detailUser.login
                tvDetailFollowers.text = detailUser.followers.toString()
                tvDetailFollowing.text = detailUser.following.toString()
                tvDetailRepository.text = detailUser.publicRepos.toString()
            }


            val sectionsPagerAdapter = ViewPagerAdapter(this)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        viewModel.loading.observe(this){
            showLoading(it)
        }

        viewModel.error.observe(this){
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
            viewModel._error.value = false
        }

    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}