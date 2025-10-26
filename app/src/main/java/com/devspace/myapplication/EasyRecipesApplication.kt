package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.local.EasyRecipesDataBase
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.main.data.RecipeMainRepository
import com.devspace.myapplication.main.data.local.RecipeMainLocalDataSource
import com.devspace.myapplication.main.data.remote.MainService
import com.devspace.myapplication.main.data.remote.RecipeMainRemoteDataSource

class EasyRecipesApplication : Application() {

   private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            EasyRecipesDataBase::class.java, name = "database-easy-recipes"
        ).build()
    }

    private val mainService by lazy {
         RetrofitClient.retrofitInstance.create(MainService::class.java)
    }

    private val localDataSource: RecipeMainLocalDataSource by lazy {
        RecipeMainLocalDataSource(db.getRecipeDao())
    }

    private val remoteDataSource: RecipeMainRemoteDataSource by lazy {
        RecipeMainRemoteDataSource(mainService)
    }

    val repository: RecipeMainRepository by lazy {
        RecipeMainRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }


}