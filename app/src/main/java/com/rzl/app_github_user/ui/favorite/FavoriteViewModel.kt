package com.rzl.app_github_user.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.data.UserRepository
import com.rzl.app_github_user.data.local.entity.UserEntity

class FavoriteViewModel (application: Application) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()


    val loading : LiveData<Boolean> = _loading

    private val userRepo : UserRepository = UserRepository(application)
    fun getFavorite() : LiveData<List<UserEntity>> = userRepo.getAllFavorites()
}