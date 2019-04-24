package com.example.yaramobile.rxjava_rxandroid

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    private var retrofit: Retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    companion object {
        const val TAG = "Retrofit"
    }

    fun showPostsSampleOne(): Observer<List<PostModel>> {
        val applicationApi = retrofit.create(ApplicationApi::class.java)
        val observable = applicationApi.getPosts()
        return observable
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeWith(object :Observer<List<PostModel>>{
                override fun onComplete() {
                    Log.e(TAG,"onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG,"onSubscribe " + d.isDisposed)
                }

                override fun onNext(t: List<PostModel>) {
                    Log.e(TAG,"onNext " + t.size)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG,"onError " + e.message)
                }
            })

    }

    fun showPostsSampleTwo(): Disposable {
        val applicationApi = retrofit.create(ApplicationApi::class.java)
        val observable = applicationApi.getPosts()
        return observable
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d(TAG, "showPostsSampleTwo ${it.size}")
            }

    }
}