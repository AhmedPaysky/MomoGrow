package com.paysky.momogrow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ProductEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "product_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(instance!!)
                }
            }
        }

        suspend fun populateDatabase(db: AppDatabase) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            val dao = db.productDao()
            dao.deleteAll()

//            var productEntity = ProductEntity(
//                "Banana", "This Banana", "150",
//                "Fruit", "15", "5", "10", "7", "56", status = "In stock",
//                qStatus = "published", false, new = true, publish = false
//            )
//            dao.insert(productEntity)
//
//            productEntity = ProductEntity(
//                "Tomato", "This Banana", "150",
//                "Fruit", "15", "5", "10", "7", "56", status = "In stock",
//                qStatus = "published", false, new = true, publish = false
//            )
//            dao.insert(productEntity)

        }

    }

}