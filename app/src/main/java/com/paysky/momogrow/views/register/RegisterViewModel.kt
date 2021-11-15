package com.paysky.momogrow.views.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.requests.MoMoPayGetMerchantInfoRequest
import com.paysky.momogrow.data.models.requests.MoMoPayRegisterAccountRequest
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val apiService: ApiService) : ViewModel() {
    val mobileNumber = MutableLiveData<String>()
    val refNumber = MutableLiveData<String>()
    val moMoPayGetMerchantInfo = MutableLiveData<MoMoPayGetMerchantInfoRequest>()
    fun moMoPayGetMerchantInfo(data: MoMoPayGetMerchantInfoRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.moMoPayGetMerchantInfo(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }

    fun moMoPayRegisterAccount(data: MoMoPayRegisterAccountRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.moMoPayRegisterAccount(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }
}