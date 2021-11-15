package com.paysky.momogrow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.views.home.HomeViewModel
import com.paysky.momogrow.views.login.LoginViewModel
import com.paysky.momogrow.views.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MobileNumberViewModel::class.java)) {
            return MobileNumberViewModel(apiService) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(apiService) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(apiService) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(apiService) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}