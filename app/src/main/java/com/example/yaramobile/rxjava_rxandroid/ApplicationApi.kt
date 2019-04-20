package com.example.yaramobile.rxjava_rxandroid

import io.reactivex.Observable
import retrofit2.http.GET

interface ApplicationApi {

    @GET("/posts")
    fun getPosts(): Observable<List<PostModel>>
}