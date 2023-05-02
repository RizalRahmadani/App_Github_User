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
import com.rzl.app_github_user.data.local.entity.UserEntity
import com.rzl.app_github_user.databinding.ActivityDetailUserBinding


class DetailUserActivity : AppCompatActivity() {
    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: DetailViewModel

    private var fabFavorite: Boolean = false
    private var favoriteUser: UserEntity? = null
    private var detailUser = ItemsItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = obtainViewModel(this@DetailUserActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTabView()


        viewModel.listUser.observe(this){
                detailList ->
            detailUser = detailList
            setDataToView(detailUser)

            favoriteUser = UserEntity(detailUser.id, detailUser.login, detailUser.avatarUrl)
            viewModel.getFavorite().observe(this){ userFavorite ->
                if (userFavorite != null){
                    for (data in userFavorite){
                        if (detailUser.id == data.id){
                            fabFavorite = true
                            binding?.fabFavorite?.setImageResource(R.drawable.ic_favorite)
                        }
                    }
                }
            }

            binding?.fabFavorite?.setOnClickListener {
                if (!fabFavorite){
                    fabFavorite = true
                    binding!!.fabFavorite.setImageResource(R.drawable.ic_favorite)
                    insertToDatabase(detailUser)
                }else{
                    fabFavorite = false
                    binding!!.fabFavorite.setImageResource(R.drawable.ic_favorit_border)
                    viewModel.delete(detailUser.id)
                    Toast.makeText(this, "Delete Favorite", Toast.LENGTH_SHORT).show()
                }
            }

        }

        viewModel.loading.observe(this){
            showLoading(it)
        }

        viewModel.error.observe(this){
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
            viewModel.doneToastError()
        }

    }

    private fun setDataToView(detailList: ItemsItem){
        binding?.let {
            Glide.with(this)
                .load(detailList.avatarUrl)
                .circleCrop()
                .into(it.imgDetailPhoto)
        }
        binding?.apply {
            tvDetailName.text = detailList.name
            tvDetailLogin.text = detailList.login
            tvDetailFollowers.text = detailList.followers.toString()
            tvDetailFollowing.text = detailList.following.toString()
            tvDetailRepository.text = detailList.publicRepos.toString()
        }
    }

    private fun setTabView(){
        val intentUser = intent.extras
        if (intentUser != null){
            val userLogin = intentUser.getString(EXTRA_USER)
            if (userLogin != null){
                viewModel.getDetailUser(userLogin)
                val login = Bundle()
                login.putString(EXTRA_FRAG, userLogin)
                val sectionPagerAdapter = ViewPagerAdapter(this, login)
                val viewPager : ViewPager2 = binding!!.viewPager
                viewPager.adapter = sectionPagerAdapter
                val tabs: TabLayout = binding!!.tabs
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    private fun insertToDatabase(detailList: ItemsItem){
        favoriteUser.let { favoriteUser ->
            favoriteUser?.id = detailList.id
            favoriteUser?.login = detailList.login
            favoriteUser?.imageUrl = detailList.avatarUrl
            viewModel.insert(favoriteUser as UserEntity)
            Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel{
        val factory = DetailViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun showLoading(isLoading : Boolean) {
        binding?.progressBar3?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAG = "extra_fragment"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}