package com.paysky.momogrow.views.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.R2
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.models.momo.requests.DispatchRequest
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class OrdersViewModel(private val apiServiceMomo: ApiServiceMomo) : ViewModel() {
    val deliveryAddress = MutableLiveData<String>()
    val grandTotal = MutableLiveData<String>()
    val pickupInstructions = MutableLiveData<String>()
    val statusDispatch = MutableLiveData<String>()
    val reference_number = MutableLiveData<String>()
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


    fun getOrderDetails(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.getOrderDetails(id)))
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

    fun cancelOrder(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.cancelOrder(id)))
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

    fun dispatchOrder(data: DispatchRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.dispatchOrder(data)))
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