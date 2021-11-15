package com.paysky.momogrow.data.api

import com.paysky.momogrow.data.models.*
import com.paysky.momogrow.data.models.requests.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("PayLink.svc/api/MoMoPayRegister")
    suspend fun moMoPayRegister(@Body registerRequest: MoMoPayRegisterRequest): MoMoPayRegisterResponse

    @POST("PayLink.svc/api/MoMoPayGetMerchantInfo")
    suspend fun moMoPayGetMerchantInfo(@Body registerRequest: MoMoPayGetMerchantInfoRequest): MoMoPayGetMerchantInfoResponse

    @POST("PayLink.svc/api/MoMoPayRegisterAccount")
    suspend fun moMoPayRegisterAccount(@Body moMoPayRegisterAccount: MoMoPayRegisterAccountRequest): MoMoPayRegisterAccountResponse

    @POST("PayLink.svc/api/MOMOPayLogin")
    suspend fun mOMOPayLogin(@Body mOMOPayLogin: MOMOPayLoginRequest): MOMOPayLoginResponse

    @POST("PayLink.svc/api/MOMOPayCheckMerchantIsRegister")
    suspend fun mOMOPayCheckMerchantIsRegister(@Body mOMOPayCheckMerchantIsRegister: MOMOPayCheckMerchantIsRegisterRequest): MOMOPayCheckMerchantIsRegisterResponse

    @POST("PayLink.svc/api/InitiateOrder")
    suspend fun initiateOrder(@Body initiateOrder: InitiateOrderRequest): InitiateOrderResponse

    @POST("PayLink.svc/api/MoMoPayAuthorizeForResetPassword")
    suspend fun moMoPayAuthorizeForResetPassword(@Body initiateOrder: MoMoPayAuthorizeForResetPasswordRequest): MoMoPayAuthorizeForResetPasswordResponse

    @POST("PayLink.svc/api/MoMoPayResetPassword")
    suspend fun moMoPayResetPassword(@Body initiateOrder: MoMoPayResetPasswordRequest): MoMoPayResetPasswordResponse

}