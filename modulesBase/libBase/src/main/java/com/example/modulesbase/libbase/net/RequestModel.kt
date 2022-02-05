package com.example.modulesbase.libbase.net

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.modulesbase.libbase.net.response.NetCallback
import com.example.modulesbase.libbase.net.response.NetResponse
import io.reactivex.rxjava3.core.Observable

private const val TAG = "RequestModel"
class RequestModel{
    companion object{
        fun <T> request(o : Observable<out NetResponse<T>>, lifecycleOwner: LifecycleOwner,
                        callback: NetCallback<T>
        ) {
            o.compose(ResponseTransformer.obtain(lifecycleOwner))
                .subscribe(
                    {callback.onSuccess(it) },
                    {
                        callback.onFailure(it)})
        }
    }
}