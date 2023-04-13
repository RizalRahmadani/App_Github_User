package com.rzl.app_github_user.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.data.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val _listUser = MutableLiveData<ItemsItem>()
    val listUser: LiveData<ItemsItem> = _listUser

    val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    val _listFollow = MutableLiveData<List<ItemsItem>>()
    val listFollow: LiveData<List<ItemsItem>> = _listFollow

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

    fun getFollowersList(username: String) {
        _loading.value = true
        val client = RetrofitApi.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _loading.value = false
                _listFollow.value = response.body()
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _loading.value = false
                _error.value = true
            }
        })
    }

    fun getFollowingList(username: String) {
        _loading.value = true
        val client = RetrofitApi.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _loading.value = false
                _listFollow.value = response.body()
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _loading.value = false
                _error.value = true
            }
        })
    }


}