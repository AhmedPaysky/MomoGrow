package com.paysky.momogrow.data.api

import com.paysky.momogrow.data.models.momo.requests.AddProductRequestModel
import com.paysky.momogrow.data.models.momo.*
import com.paysky.momogrow.data.models.momo.orders.DispatchResponse
import com.paysky.momogrow.data.models.momo.orders.OrderDetailsResponse
import com.paysky.momogrow.data.models.momo.orders.OrdersResponse
import com.paysky.momogrow.data.models.momo.requests.DispatchRequest
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiServiceMomo {
    @GET("/service/admin/catalog/attribute-families")
    suspend fun getAttributeFamilies(): AttributesFamiliesMainModel

    @GET("/service/admin/catalog/products")
    suspend fun getAllProducts(): ProductsResponse

    @GET("/service/admin/catalog/products")
    suspend fun getAllProductsWithID(@Query("category_id") id: Int): ProductsResponse

    @GET("/service/admin/catalog/categories")
    suspend fun getAllCategories(): MainOfMainCategories

    @GET("/service/admin/sale/orders")
    suspend fun getAllOrders(): OrdersResponse


    @GET("/service/admin/sale/orders/show/{id}")
    suspend fun getOrderDetails(@Path("id") id: Int): OrderDetailsResponse

    @GET("/service/admin/catalog/products/show/{id}")
    suspend fun getProductDetails(@Path("id") id: String): ProductsResponse

    @GET("/service/admin/catalog/products/show/{id}")
    suspend fun getOrderDetails(@Path("id") id: String): ProductsResponse

    @DELETE("/service/admin/catalog/products/delete/{id}")
    suspend fun deleteProduct(@Path("id") id: String): SimpleResponse

    @GET("/service/admin/sale/orders/cancel/{id}")
    suspend fun cancelOrder(@Path("id") id: Int): SimpleResponse


    @POST("/service/admin/catalog/products/create")
    suspend fun AddProduct(@Body body: AddProductRequestModel): AddProductResponse

    @POST("/service/admin/sale/create-shipment")
    suspend fun dispatchOrder(@Body body: DispatchRequest): DispatchResponse

    @POST("/service/admin/catalog/products/update/{id}")
    suspend fun UpdateProduct(
        @Path("id") id: Int,
        @Body body: AddProductRequestModel
    ): AddProductResponse

    @Multipart
    @POST("/service/admin/catalog/products/images/{id}/add")
    suspend fun AddImagesToProduct(
        @Path("id") id: Int,
        @Part images: ArrayList<MultipartBody.Part>
    ): EmptyResponse
}