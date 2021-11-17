package com.paysky.momogrow.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.data.api.ApiServiceCube
import com.paysky.momogrow.data.models.requests.MOMOPayLoginRequest
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val apiServiceCube: ApiServiceCube) : ViewModel() {
    fun mOMOPayLogin(data: MOMOPayLoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceCube.mOMOPayLogin(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }
}