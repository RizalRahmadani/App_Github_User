package com.rzl.app_github_user.data.remote.retrofit

import com.rzl.app_github_user.GithubUserResponse
import com.rzl.app_github_user.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getUserList() : Call<List<ItemsItem>>

    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ) : Call<GithubUserResponse>


    @GET("users/{username}")
    fun detailUsers(
        @Path("username") username : String
    ): Call<ItemsItem>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}