package com.paysky.momogrow.data.api

import com.paysky.momogrow.data.models.AddProductRequestModel
import com.paysky.momogrow.data.models.momo.*
import com.paysky.momogrow.data.models.momo.orders.OrdersResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiServiceMomo {
    @GET("/service/admin/catalog/attribute-families")
    suspend fun getAttributeFamilies(): AttributesFamiliesMainModel


    @GET("/service/admin/catalog/products")
    suspend fun getAllProducts(): ProductsResponse

    @GET("service/admin/catalog/categories")
    suspend fun getAllCategories(): ArrayList<CategoriesItem>

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


    @POST("/service/admin/catalog/products/create")
    suspend fun AddProduct(@Body body: AddProductRequestModel): AddProductResponse

    @Multipart
    @POST("/service/admin/catalog/products/images/{id}/add")
    suspend fun AddImagesToProduct(@Path("id") id: Int,@Part images: ArrayList<MultipartBody.Part>): AddProductResponse


//    @Multipart
//    @POST("androiduploadfile.php")
//    fun doUploadProfilePicture(
//        @Part("token") token: RequestBody?,
//        @Part("action") action: RequestBody?,
//        @Part("clientId") clientId: RequestBody?,
//        @Part file: MultipartBody.Part?
//    ): Call<ImageResponse?>?

}