package com.linoop.composablemvvm.di

import android.content.Context
import com.linoop.composablemvvm.network.MyApi
import com.linoop.composablemvvm.network.UrlInterceptor
import com.linoop.composablemvvm.repository.MyRepo
import com.linoop.composablemvvm.storage.SharedPrefManager
import com.linoop.composablemvvm.utils.Constants.BASE_URL
import com.linoop.composablemvvm.utils.Constants.MY_APP_PREF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(MY_APP_PREF, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideRepository(api: MyApi) = MyRepo(api)

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): MyApi =
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(MyApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun provideUrlInterceptor(sharedPrefManager: SharedPrefManager): Interceptor =
        UrlInterceptor(sharedPrefManager)
}