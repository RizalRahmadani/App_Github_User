package com.rzl.app_github_user.data

import com.rzl.app_github_user.GithubUserResponse
import com.rzl.app_github_user.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUserList() : Call<List<ItemsItem>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q") query: String
    ) : Call<GithubUserResponse>


    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun detailUsers(
        @Path("username") username : String
    ): Call<ItemsItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>


}