package com.rzl.app_github_user.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.data.UserRepository
import com.rzl.app_github_user.data.local.entity.UserEntity
import com.rzl.app_github_user.data.remote.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : ViewModel() {
    val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val _listUser = MutableLiveData<ItemsItem>()
    val listUser: LiveData<ItemsItem> = _listUser

    val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    val _listFollow = MutableLiveData<List<ItemsItem>>()
    val listFollow: LiveData<List<ItemsItem>> = _listFollow


    private val mFavoriteUserRepo: UserRepository =
        UserRepository(application)

    fun insert(user: UserEntity){
        mFavoriteUserRepo.insert(user)
    }

    fun delete(id: Int){
        mFavoriteUserRepo.delete(id)
    }

    fun getFavorite(): LiveData<List<UserEntity>> =
        mFavoriteUserRepo.getAllFavorites()

    fun getDetailUser(username: String) {
        _loading.value = true
        val client = RetrofitApi.getApiService().detailUsers(username)
        client.enqueue(object : Callback<ItemsItem> {
            override fun onResponse(
                call: Call<ItemsItem>,
                response: Response<ItemsItem>
            ) {
                if (response.isSuccessful) {
                    _loading.value = false
                    _listUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                _loading.value = false
                _error.value = true
            }

        })
    }

    fun doneToastError(){
        _error.value = false
    }




}