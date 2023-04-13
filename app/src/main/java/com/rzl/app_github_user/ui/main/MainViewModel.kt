package com.rzl.app_github_user.ui.main


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rzl.app_github_user.GithubUserResponse
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.data.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel(){

    val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers : LiveData<List<ItemsItem>> = _listUsers

    val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean> = _error


    companion object{
        private const val TAG = "MainViewModel"
    }

    init {

    }




    fun searchUser(query: String){
        _loading.value = true
        val client = RetrofitApi.getApiService().searchUser(query)
        client.enqueue(object : Callback<GithubUserResponse>{

            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                if(response.isSuccessful){
                    _loading.value = false
                    _listUsers.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
               _loading.value = false
                Log.e(TAG, "onFailure: ${t.message}")

            }

        })
    }

    fun detailUser(){
        _loading.value = true
        val client = RetrofitApi.getApiService().getUserList()
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    _loading.value = false
                    _listUsers.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
               _loading.value = false
                _error.value = true
            }

        })
    }





}