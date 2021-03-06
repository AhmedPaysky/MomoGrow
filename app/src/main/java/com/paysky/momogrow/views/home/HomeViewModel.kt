package com.paysky.momogrow.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.data.api.ApiServiceCube
import com.paysky.momogrow.data.models.requests.BuildChartRequest
import com.paysky.momogrow.data.models.requests.InitiateOrderRequest
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class HomeViewModel(private val apiServiceCube: ApiServiceCube) : ViewModel() {

    fun initiateOrderRequest(data: InitiateOrderRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceCube.initiateOrder(data)))
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



    fun CallBuildChart(data: BuildChartRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceCube.showChartData(data)))
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