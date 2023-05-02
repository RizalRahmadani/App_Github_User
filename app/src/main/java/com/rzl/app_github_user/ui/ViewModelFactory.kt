package com.rzl.app_github_user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rzl.app_github_user.ui.settings.SettingPreference
import com.rzl.app_github_user.ui.settings.SettingViewModel

class ViewModelFactory(private val pref : SettingPreference) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : " + modelClass.name )
    }
}