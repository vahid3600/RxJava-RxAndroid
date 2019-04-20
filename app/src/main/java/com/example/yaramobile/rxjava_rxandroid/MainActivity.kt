package com.example.yaramobile.rxjava_rxandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var observable: Observable<Int>? = null

    companion object {
        val TAG = MainActivity::class.qualifiedName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Retrofit().showPosts()

        Observable.fromArray(listOf(1, 2, 3, 4))

        observable = Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

        observable?.subscribe(object : Observer<Int> {
            override fun onComplete() {
                Log.e(TAG, "onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Log.e(TAG, "onSubscribe")
            }

            override fun onNext(t: Int) {
                Log.e(TAG, "onNext $t")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError")
            }

        })

        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {

                for (i in 1..4)
                    emitter.onNext(i)
            }

        })

        CompositeDisposable(
            Observable.just(1, 2, 3, 4).subscribe(),
            Observable.just(1, 2, 3, 4).subscribe(),
            Observable.just(1, 2, 3, 4).subscribe()
        )

        RxTextView.textChanges(editText)
            .subscribe {
                Log.e(TAG, it.toString())
            }

    }
}