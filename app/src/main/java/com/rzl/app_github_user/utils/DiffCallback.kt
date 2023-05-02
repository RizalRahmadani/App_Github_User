package com.rzl.app_github_user.utils

import androidx.recyclerview.widget.DiffUtil
import com.rzl.app_github_user.data.local.entity.UserEntity


class DiffCallback(private val mOldList: List<UserEntity>, private val mNewList: List<UserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].id == mNewList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = mOldList[oldItemPosition]
        val newList = mNewList[newItemPosition]
        return oldList.login == newList.login && oldList.imageUrl == newList.imageUrl
    }
}