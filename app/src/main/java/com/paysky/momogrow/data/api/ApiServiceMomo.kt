package com.paysky.momogrow.data.api

import com.paysky.momogrow.data.models.MoMoPayRegisterResponse
import com.paysky.momogrow.data.models.momo.AddProductResponse
import com.paysky.momogrow.data.models.momo.ProductsResponse
import com.paysky.momogrow.data.models.momo.SimpleResponse
import com.paysky.momogrow.data.models.momo.orders.OrdersResponse
import com.paysky.momogrow.data.models.requests.MoMoPayRegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.*

interface ApiServiceMomo {
    @GET("/service/admin/catalog/products")
    suspend fun getAllProducts(): ProductsResponse

    @GET("/service/admin/catalog/orders")
    suspend fun getAllOrders(): OrdersResponse

    @GET("/api/admin/catalog/products/show/{id}")
    suspend fun getProductDetails(@Path("id") id: String): ProductsResponse

    @GET("/api/admin/catalog/products/show/{id}")
    suspend fun getOrderDetails(@Path("id") id: String): ProductsResponse

    @DELETE("/api/admin/catalog/products/delete/{id}")
    suspend fun deleteProduct(@Path("id") id: String): SimpleResponse

    @DELETE("/api/admin/catalog/products/delete/{id}")
    suspend fun cancelOrder(@Path("id") id: String): SimpleResponse

//    @Multipart
//    @POST("androiduploadfile.php")
//    fun doUploadProfilePicture(
//        @Part("token") token: RequestBody?,
//        @Part("action") action: RequestBody?,
//        @Part("clientId") clientId: RequestBody?,
//        @Part file: MultipartBody.Part?
//    ): Call<ImageResponse?>?

    @Multipart
    @POST("androiduploadfile.php")
    suspend fun AddProduct(@Part("id") id: String): AddProductResponse

}