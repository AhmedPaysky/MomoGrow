package com.paysky.momogrow.viewmodels

import androidx.lifecycle.ViewModel
import com.paysky.momogrow.data.api.ApiService
import kotlinx.coroutines.Dispatchers


import androidx.lifecycle.liveData
import com.paysky.momogrow.data.models.requests.MOMOPayCheckMerchantIsRegisterRequest
import com.paysky.momogrow.data.models.requests.MoMoPayAuthorizeForResetPasswordRequest
import com.paysky.momogrow.data.models.requests.MoMoPayRegisterRequest
import com.paysky.momogrow.data.models.requests.MoMoPayResetPasswordRequest
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

    fun moMoPayAuthorizeForResetPassword(data: MoMoPayAuthorizeForResetPasswordRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = apiService.moMoPayAuthorizeForResetPassword(data)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
            }
        }

    fun moMoPayResetPassword(data: MoMoPayResetPasswordRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = apiService.moMoPayResetPassword(data)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
            }
        }

}