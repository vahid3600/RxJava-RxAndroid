package com.example.yaramobile.rxjava_rxandroid

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    lateinit var retrofit: Retrofit

    companion object {
        const val TAG = "Retrofit"
    }

    init {
        retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun showPosts() {
        val applicationApi = retrofit.create(ApplicationApi::class.java)
        val observable = applicationApi.getPosts()
        var disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e(TAG,"showPosts " + it.size)
            }

    }
}