package com.paysky.momogrow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paysky.momogrow.data.api.ApiServiceCube
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.views.home.HomeViewModel
import com.paysky.momogrow.views.login.LoginViewModel
import com.paysky.momogrow.views.orders.OrdersViewModel
import com.paysky.momogrow.views.products.ProductViewModel
import com.paysky.momogrow.views.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryCube(private val apiServiceCube: ApiServiceCube) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MobileNumberViewModel::class.java)) {
            return MobileNumberViewModel(apiServiceCube) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(apiServiceCube) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(apiServiceCube) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(apiServiceCube) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}

class ViewModelFactoryMomo(private val apiServiceMomo: ApiServiceMomo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(apiServiceMomo) as T
        } else if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel(apiServiceMomo) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}
