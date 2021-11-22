package com.paysky.momogrow.views.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.MyApplication
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.local.ProductEntity
import com.paysky.momogrow.data.models.AddProductRequestModel
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import retrofit2.HttpException

class ProductViewModel(private val apiServiceMomo: ApiServiceMomo) : ViewModel() {
    val dao = MyApplication.db.productDao()

    //    fun allProducts() = dao.getAllProducts()
    fun getProductById(id: Int) = dao.getProductById(id)
    suspend fun deleteProduct(it: ProductEntity) = dao.delete(it)

    fun allProducts(id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (id == 0) {
                emit(Resource.success(data = apiServiceMomo.getAllProducts()))
            }
            else {
                emit(Resource.success(data = apiServiceMomo.getAllProductsWithID(id)))
            }
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
    fun allCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.getAllCategories()))
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
    fun allAttributesFamilies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.getAttributeFamilies()))
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
    fun addproduct(productEntity: AddProductRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.AddProduct(productEntity)))
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
    fun updateproduct(productid: Int,productEntity: AddProductRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.UpdateProduct(productid,productEntity)))
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
    fun addImagesToProduct(id : Int,productEntity: ArrayList<MultipartBody.Part>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.AddImagesToProduct(id,productEntity)))
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

    fun deleteProduct(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.deleteProduct(id)))
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