package com.paysky.momogrow.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paysky.momogrow.data.api.ApiService
import kotlinx.coroutines.Dispatchers


import androidx.lifecycle.liveData
import com.paysky.momogrow.data.models.MOMOPayCheckMerchantIsRegisterRequest
import com.paysky.momogrow.data.models.MoMoPayRegisterRequest
import com.paysky.momogrow.helper.Resource


class MobileNumberViewModel(private val apiService: ApiService) : ViewModel() {

    fun moMoPayRegister(data: MoMoPayRegisterRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.moMoPayRegister(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }

    fun mOMOPayCheckMerchantIsRegister(data: MOMOPayCheckMerchantIsRegisterRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = apiService.mOMOPayCheckMerchantIsRegister(data)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
            }
        }

}