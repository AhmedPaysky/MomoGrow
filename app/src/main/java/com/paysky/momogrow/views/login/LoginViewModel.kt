package com.paysky.momogrow.views.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.MOMOPayLoginRequest
import com.paysky.momogrow.data.models.MoMoPayGetMerchantInfoRequest
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val apiService: ApiService) : ViewModel() {
    fun mOMOPayLogin(data: MOMOPayLoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.mOMOPayLogin(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }
}