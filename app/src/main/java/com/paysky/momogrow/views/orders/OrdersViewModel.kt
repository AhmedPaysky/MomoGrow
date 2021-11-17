package com.paysky.momogrow.views.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class OrdersViewModel(private val apiServiceMomo: ApiServiceMomo) : ViewModel() {
    fun getAllOrders() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.getAllOrders()))
        } catch (exception: HttpException) {
            emit(
                Resource.errorHttp(
                    data = exception,
                    message = exception.message ?: "Error occured"
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }

}