package com.rzl.app_github_user.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.rzl.app_github_user.data.local.entity.UserEntity
import com.rzl.app_github_user.data.local.room.UserDao
import com.rzl.app_github_user.data.local.room.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mFavoriteUserDao : UserDao
    private val excutorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mFavoriteUserDao = db.userDao()
    }
    fun getAllFavorites(): LiveData<List<UserEntity>> = mFavoriteUserDao.getAllUser()

    fun insert(user: UserEntity){
        excutorService.execute { mFavoriteUserDao.insertFavorite(user) }
    }
    fun delete(id: Int){
        excutorService.execute { mFavoriteUserDao.removeFavorite(id) }
    }
}