package com.example.modulesbase.libbase.net

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.modulesbase.libbase.net.exception.ApiException
import com.example.modulesbase.libbase.net.response.NetResponse
import com.example.modulesbase.libbase.util.ReflectUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ResponseTransformer<T> : ObservableTransformer<NetResponse<T>, T>, DefaultLifecycleObserver
{

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    override fun apply(upstream: Observable<NetResponse<T>>): ObservableSource<T> {
        return upstream.doOnSubscribe { compositeDisposable.add(it) }
            .onErrorResumeNext { Observable.error(it)}
            .flatMap {
                if(it.isSuccess){
                    if(it.data != null)
                    {
                            Observable.just(it.data)
                    }
                    else
                    {
                        val clz = ReflectUtil.analysisClassInfo(it)
                        val obj: T = clz.newInstance() as T
                        Observable.just(obj)
                    }
                }
                else{
                    Observable.error(
                        ApiException.handleException(
                        ApiException(it.code, it.msg)
                    ))
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    companion object{
        inline fun <T> obtain(lifecycleOwner: LifecycleOwner): ResponseTransformer<T>
        {
            val transformer = ResponseTransformer<T>()
            lifecycleOwner.lifecycle.addObserver(transformer)
            return transformer
        }
    }
}