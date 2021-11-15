package com.paysky.momogrow.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(productEntity: ProductEntity): Long

    @Update
    suspend fun update(productEntity: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)

    @Query("delete from product_table")
    suspend fun deleteAllProducts()

    @Query("select * from product_table")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query("select * from product_table WHERE id=:id")
    fun getProductById(id: Int): LiveData<ProductEntity>

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()
}